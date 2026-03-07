#!/bin/bash

# Run examples script

echo "🎓 Running Concurrency Examples..."
echo ""

# Verificar se está compilado
if [ ! -d "target/classes" ]; then
    echo "⚠️  Project not compiled yet. Running build first..."
    ./build.sh
    if [ $? -ne 0 ]; then
        exit 1
    fi
fi

# Executar os exemplos
java -cp target/classes org.example.examples.ConcurrencyExamples

