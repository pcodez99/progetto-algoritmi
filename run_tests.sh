#!/bin/sh
# Compila il progetto ed esegue ogni test/inputN*.txt confrontandolo con test/expectedN*.txt
cd "$(dirname "$0")" || exit 1
javac *.java || exit 1
fail=0
for in in test/esempioInputProfessoeessa.txt test/input*.txt; do
    case "$in" in
        test/esempioInputProfessoeessa.txt) exp=test/expected1_esempio_traccia.txt ;;
        *) exp=$(echo "$in" | sed 's/input/expected/') ;;
    esac
    if java Main < "$in" | diff -q - "$exp" > /dev/null; then
        echo "OK    $in"
    else
        echo "FAIL  $in"; fail=1
    fi
done
exit $fail
