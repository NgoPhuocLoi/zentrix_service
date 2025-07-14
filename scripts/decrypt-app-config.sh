#!/bin/bash

# Script to decrypt application.yaml.gpg using GPG symmetric decryption
# This script prompts for a passphrase and decrypts the configuration file

set -e  # Exit on any error

# Define file paths
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
ENCRYPTED_FILE="$PROJECT_ROOT/application.yaml.gpg"
DECRYPTED_FILE="$PROJECT_ROOT/src/main/resources/application.yaml"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${YELLOW}GPG Symmetric Decryption for Application Configuration${NC}"
echo "===================================================="

# Check if the encrypted file exists
if [ ! -f "$ENCRYPTED_FILE" ]; then
    echo -e "${RED}Error: Encrypted file not found at $ENCRYPTED_FILE${NC}"
    echo "Please run the encryption script first or ensure the encrypted file exists."
    exit 1
fi

# Check if GPG is installed
if ! command -v gpg &> /dev/null; then
    echo -e "${RED}Error: GPG is not installed. Please install GPG first.${NC}"
    echo "On macOS: brew install gnupg"
    exit 1
fi

echo "Encrypted file: $ENCRYPTED_FILE"
echo "Decrypted output: $DECRYPTED_FILE"
echo

# Check if the decrypted file already exists and prompt for confirmation
if [ -f "$DECRYPTED_FILE" ]; then
    echo -e "${YELLOW}Warning: The file $DECRYPTED_FILE already exists.${NC}"
    read -p "Do you want to overwrite it? (y/N): " confirm
    case $confirm in
        [Yy]* ) 
            echo "Proceeding with decryption..."
            ;;
        * ) 
            echo "Decryption cancelled."
            exit 0
            ;;
    esac
    echo
fi

# Prompt for passphrase (hidden input)
echo -e "${YELLOW}Please enter the decryption passphrase:${NC}"
read -s -p "Passphrase: " passphrase
echo

# Check if passphrase is not empty
if [ -z "$passphrase" ]; then
    echo -e "${RED}Error: Passphrase cannot be empty!${NC}"
    exit 1
fi

echo -e "${YELLOW}Decrypting configuration file...${NC}"

# Create the target directory if it doesn't exist
TARGET_DIR="$(dirname "$DECRYPTED_FILE")"
if [ ! -d "$TARGET_DIR" ]; then
    echo "Creating directory: $TARGET_DIR"
    mkdir -p "$TARGET_DIR"
fi

# Decrypt the file using GPG symmetric decryption
if echo "$passphrase" | gpg --batch --yes --passphrase-fd 0 --decrypt "$ENCRYPTED_FILE" > "$DECRYPTED_FILE"; then
    echo -e "${GREEN}✓ Configuration file decrypted successfully!${NC}"
    echo "Decrypted file saved as: $DECRYPTED_FILE"
    
    # Display file sizes
    encrypted_size=$(stat -f%z "$ENCRYPTED_FILE" 2>/dev/null || stat -c%s "$ENCRYPTED_FILE" 2>/dev/null)
    decrypted_size=$(stat -f%z "$DECRYPTED_FILE" 2>/dev/null || stat -c%s "$DECRYPTED_FILE" 2>/dev/null)
    
    echo
    echo "File sizes:"
    echo "  Encrypted: $encrypted_size bytes"
    echo "  Decrypted: $decrypted_size bytes"
    
    # Show first few lines of the decrypted file (without sensitive data)
    echo
    echo -e "${BLUE}First few lines of decrypted file:${NC}"
    head -n 5 "$DECRYPTED_FILE" | sed 's/password:.*/password: [HIDDEN]/' | sed 's/secret:.*/secret: [HIDDEN]/' | sed 's/key:.*/key: [HIDDEN]/'
    
    echo
    echo -e "${YELLOW}Important Security Notes:${NC}"
    echo "1. The decrypted file contains sensitive configuration data"
    echo "2. Ensure the decrypted file is not committed to version control"
    echo "3. Consider deleting the decrypted file after use in production"
    echo "4. Keep the encrypted file (.gpg) as your backup"
else
    echo -e "${RED}✗ Decryption failed!${NC}"
    echo "This could be due to:"
    echo "1. Incorrect passphrase"
    echo "2. Corrupted encrypted file"
    echo "3. File was not encrypted with symmetric encryption"
    
    # Clean up partial file if it exists
    if [ -f "$DECRYPTED_FILE" ] && [ ! -s "$DECRYPTED_FILE" ]; then
        rm -f "$DECRYPTED_FILE"
        echo "Cleaned up empty decrypted file."
    fi
    exit 1
fi

# Clear passphrase variable for security
unset passphrase

echo -e "${GREEN}Decryption process completed.${NC}"
