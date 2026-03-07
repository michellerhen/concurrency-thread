#!/bin/bash

# Run script para executar o projeto

echo "🚀 Running Concurrency Thread Lab..."
echo ""

# Verificar se está compilado
if [ ! -d "target/classes" ]; then
    echo "⚠️  Project not compiled yet. Running build first..."
    ./build.sh
    if [ $? -ne 0 ]; then
        exit 1
    fi
fi

# Executar o projeto
java -cp target/classes org.example.Main

