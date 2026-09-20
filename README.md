Project layout
* src/Algorithms/   MergeSort, QuickSort, QuickSelect, InsertionSort, Partitioning 
* src/Metrics/      Metrics (comparisons, recursion depth, time)
* src/Benchmark/    Benchmark (runs everything, writes results.csv)
* src/Test/         JUnit 5 tests
* results.csv                 Benchmark output (algorithm,input,n,time_ms,comparisons,max_depth)
* REPORT.pdf                  Asymptotic bounds, recurrences, Theta check, discussion

1) Run the tests src/Test/
Runs all JUnit 5 tests: correctness vs. Arrays.sort on 100+ random arrays per algorithm, edge cases (empty / single-element / all-equal / already-sorted), the QuickSort recursion-depth bound on a sorted array of 100 000 elements, and QuickSelect correctness against Arrays.sort plus invalid-input handling.

2) Run the benchmark src/Benchmark/ 
Run MergeSort, QuickSort and QuickSelect over n ∈ {1 000, 10 000, 100 000, 1 000 000} and input types random / sorted / duplicates (values 0–9), take the median of 5 repeats per case, and write results.csv in the project root with columns:algorithm,input,n,time_ms,comparisons,max_depth



Design highlights

* MergeSort allocates its helper buffer exactly once (in the top-level call) and reuses it through the whole recursion; subarrays of length ≤ 15 are sorted with Insertion Sort instead of recursing further.
* QuickSort picks a uniformly random pivot every call, partitions in one linear 3-way (Dutch-flag) pass so arrays with many duplicate keys stay fast, and always recurses into the smaller side while continuing the larger side in a loop in the same stack frame — this bounds recursion depth to O(log n) even on already-sorted input, so it never StackOverflows.
* QuickSelect reuses the exact same partition routine as QuickSort and, after partitioning, continues in a loop (not a fresh recursive call) only in whichever side contains the target index k, giving expected O(n) time and O(1) extra recursion depth.
* Metrics is a small, non-static object passed explicitly into every algorithm call, so comparisons / recursion depth / elapsed time can be measured per-run without any global mutable state.

See REPORT.md for the full asymptotic analysis, recurrences (Master Theorem), plots and discussion of measured vs. theoretical behavior.
