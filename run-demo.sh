#!/bin/bash

# Run script para executar o Quick Demo

echo "🚀 Running Quick Demo (Fast benchmark)..."
echo ""

# Verificar se está compilado
if [ ! -d "target/classes" ]; then
    echo "⚠️  Project not compiled yet. Running build first..."
    ./build.sh
    if [ $? -ne 0 ]; then
        exit 1
    fi
fi

# Executar o Quick Demo
java -cp target/classes org.example.QuickDemo

