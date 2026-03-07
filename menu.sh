#!/bin/bash

# Menu interativo para execução do projeto

clear
echo "════════════════════════════════════════════════════════════════"
echo "   🚀 Concurrency & Thread Management Lab - Java 21"
echo "════════════════════════════════════════════════════════════════"
echo ""
echo "Escolha uma opção:"
echo ""
echo "  1) 🏃 Quick Demo (rápido, ~30s)"
echo "  2) 📊 Benchmark Completo (completo, ~5min)"
echo "  3) 🎓 Exemplos Práticos de Concorrência"
echo "  4) 🔬 Custom Benchmark Example"
echo "  5) 🔨 Recompilar projeto"
echo "  6) 📖 Abrir documentação"
echo "  0) ❌ Sair"
echo ""
echo "════════════════════════════════════════════════════════════════"
echo -n "Sua escolha: "
read choice

case $choice in
    1)
        echo ""
        echo "🏃 Executando Quick Demo..."
        echo ""
        ./run-demo.sh
        ;;
    2)
        echo ""
        echo "📊 Executando Benchmark Completo..."
        echo "⚠️  Isso pode levar alguns minutos..."
        echo ""
        ./run.sh
        ;;
    3)
        echo ""
        echo "🎓 Executando Exemplos Práticos..."
        echo ""
        java -cp target/classes org.example.examples.ConcurrencyExamples
        ;;
    4)
        echo ""
        echo "🔬 Executando Custom Benchmark..."
        echo ""
        java -cp target/classes org.example.CustomBenchmarkExample
        ;;
    5)
        echo ""
        ./build.sh
        ;;
    6)
        echo ""
        echo "📖 Documentação disponível:"
        echo ""
        echo "  • README.md           - Visão geral do projeto"
        echo "  • QUICKSTART.md       - Guia de execução rápida"
        echo "  • CONFIGURATION.md    - Como configurar os testes"
        echo "  • ANALYSIS.md         - Análise detalhada dos resultados"
        echo "  • TROUBLESHOOTING.md  - Solução de problemas"
        echo ""
        echo "Abrir qual arquivo? (ou Enter para voltar)"
        read -r doc
        if [ ! -z "$doc" ]; then
            if [ -f "$doc" ]; then
                cat "$doc" | less
            else
                echo "❌ Arquivo não encontrado: $doc"
            fi
        fi
        ;;
    0)
        echo ""
        echo "👋 Até logo!"
        exit 0
        ;;
    *)
        echo ""
        echo "❌ Opção inválida!"
        exit 1
        ;;
esac

echo ""
echo "════════════════════════════════════════════════════════════════"
echo "Pressione Enter para sair..."
read

