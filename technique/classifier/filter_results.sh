for nc in `cat non-conformances.txt`; do
    grep -A1 "**Different values(${nc})" ../results/diff/* > results/${nc}.txt
    grep -A1 "**Specific value mismatch(${nc})" ../results/diff/* >> results/${nc}.txt
done
