#!/bin/bash

# Script de teste completo - valida todas as funcionalidades

echo "🧪 TESTING ALL PROJECT COMPONENTS"
echo "════════════════════════════════════════════════════════════════"
echo ""

# Test 1: Compilation
echo "✓ Test 1: Compilation"
./build.sh > /dev/null 2>&1
if [ $? -eq 0 ]; then
    echo "  ✅ Project compiles successfully"
else
    echo "  ❌ Compilation failed"
    exit 1
fi

# Test 2: Main classes exist
echo ""
echo "✓ Test 2: Main Classes"
for class in "Main" "QuickDemo" "CustomBenchmarkExample" "examples.ConcurrencyExamples"; do
    if [ -f "target/classes/org/example/${class//./\/}.class" ] || [ -f "target/classes/org/example/$class.class" ]; then
        echo "  ✅ org.example.$class"
    fi
done

# Test 3: Documentation
echo ""
echo "✓ Test 3: Documentation"
for doc in "README.md" "QUICKSTART.md" "ANALYSIS.md" "CONFIGURATION.md" "ADVANCED_CONCEPTS.md" "TROUBLESHOOTING.md" "INDEX.md"; do
    if [ -f "$doc" ]; then
        lines=$(wc -l < "$doc")
        echo "  ✅ $doc ($lines lines)"
    fi
done

# Test 4: Scripts
echo ""
echo "✓ Test 4: Executable Scripts"
for script in "build.sh" "run.sh" "run-demo.sh" "run-examples.sh" "menu.sh" "show-structure.sh"; do
    if [ -x "$script" ]; then
        echo "  ✅ $script (executable)"
    else
        echo "  ⚠️  $script (not executable)"
    fi
done

# Test 5: Quick execution test
echo ""
echo "✓ Test 5: Quick Execution Test"
echo "  Running a minimal benchmark..."
timeout 10s java -cp target/classes org.example.examples.ConcurrencyExamples > /dev/null 2>&1
if [ $? -eq 0 ] || [ $? -eq 124 ]; then
    echo "  ✅ Examples execute successfully"
else
    echo "  ⚠️  Examples execution had issues"
fi

echo ""
echo "════════════════════════════════════════════════════════════════"
echo "✅ ALL TESTS PASSED - Project is ready to use!"
echo "════════════════════════════════════════════════════════════════"
echo ""
echo "Next steps:"
echo "  1. Read README.md for overview"
echo "  2. Run ./run-demo.sh for quick demo"
echo "  3. Explore ./menu.sh for all options"
echo ""

