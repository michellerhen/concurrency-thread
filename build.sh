#!/bin/bash

# Build script para compilar o projeto sem Maven

echo "🔨 Building Concurrency Thread Lab..."
echo ""

# Criar diretório de output
mkdir -p target/classes

# Compilar todos os arquivos Java recursivamente
echo "Compiling Java files..."
find src/main/java -name "*.java" -print | xargs javac -d target/classes -sourcepath src/main/java

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo ""
    echo "To run the project, execute:"
    echo "  ./run.sh       - Full benchmark"
    echo "  ./run-demo.sh  - Quick demo"
    echo "  ./menu.sh      - Interactive menu"
else
    echo "❌ Compilation failed!"
    exit 1
fi

