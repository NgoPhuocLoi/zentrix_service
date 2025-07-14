#!/bin/bash

# Script to encrypt application.yaml using GPG symmetric encryption
# This script prompts for a passphrase and encrypts the configuration file

set -e  # Exit on any error

# Define file paths
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
CONFIG_FILE="$PROJECT_ROOT/src/main/resources/application.yaml"
ENCRYPTED_FILE="$PROJECT_ROOT/application.yaml.gpg"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}GPG Symmetric Encryption for Application Configuration${NC}"
echo "================================================="

# Check if the configuration file exists
if [ ! -f "$CONFIG_FILE" ]; then
    echo -e "${RED}Error: Configuration file not found at $CONFIG_FILE${NC}"
    exit 1
fi

# Check if GPG is installed
if ! command -v gpg &> /dev/null; then
    echo -e "${RED}Error: GPG is not installed. Please install GPG first.${NC}"
    echo "On macOS: brew install gnupg"
    exit 1
fi

echo "Configuration file: $CONFIG_FILE"
echo "Encrypted output: $ENCRYPTED_FILE"
echo

# Prompt for passphrase (hidden input)
echo -e "${YELLOW}Please enter the encryption passphrase:${NC}"
read -s -p "Passphrase: " passphrase
echo

# Verify passphrase
echo -e "${YELLOW}Please confirm the passphrase:${NC}"
read -s -p "Confirm passphrase: " passphrase_confirm
echo

# Check if passphrases match
if [ "$passphrase" != "$passphrase_confirm" ]; then
    echo -e "${RED}Error: Passphrases do not match!${NC}"
    exit 1
fi

# Check if passphrase is not empty
if [ -z "$passphrase" ]; then
    echo -e "${RED}Error: Passphrase cannot be empty!${NC}"
    exit 1
fi

echo -e "${YELLOW}Encrypting configuration file...${NC}"

# Encrypt the file using GPG symmetric encryption
if echo "$passphrase" | gpg --batch --yes --passphrase-fd 0 --cipher-algo AES256 --compress-algo 1 --symmetric --output "$ENCRYPTED_FILE" "$CONFIG_FILE"; then
    echo -e "${GREEN}✓ Configuration file encrypted successfully!${NC}"
    echo "Encrypted file saved as: $ENCRYPTED_FILE"
    
    # Display file sizes
    original_size=$(stat -f%z "$CONFIG_FILE" 2>/dev/null || stat -c%s "$CONFIG_FILE" 2>/dev/null)
    encrypted_size=$(stat -f%z "$ENCRYPTED_FILE" 2>/dev/null || stat -c%s "$ENCRYPTED_FILE" 2>/dev/null)
    
    echo
    echo "File sizes:"
    echo "  Original: $original_size bytes"
    echo "  Encrypted: $encrypted_size bytes"
    
    echo
    echo -e "${YELLOW}Important Security Notes:${NC}"
    echo "1. Store your passphrase securely - you'll need it to decrypt the file"
    echo "2. Consider adding *.gpg files to your .gitignore if not already present"
    echo "3. Never commit the original application.yaml to version control in production"
    echo "4. To decrypt: gpg --decrypt $ENCRYPTED_FILE > application.yaml"
else
    echo -e "${RED}✗ Encryption failed!${NC}"
    exit 1
fi

# Clear passphrase variables for security
unset passphrase
unset passphrase_confirm

echo -e "${GREEN}Encryption process completed.${NC}"