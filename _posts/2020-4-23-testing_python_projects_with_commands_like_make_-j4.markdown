---
title: Testing Python projects with commands like make -j4
category: programming
---

![test python with GNU Make](/blog/images/test_python_with_make.png)

Having used pytest, nose, nose2 for many months and faced random issues like some tests would fail when running in a set (not concurrently) together with all other tests but pass if ran alone (whereas the target use case was to be run in its own process alone), and also random issues in getting tests to continue from the last failed one (in larger projects with 300+ integration tests) - I was looking for a solution that can run tests concurrently, in their own processes, and can continue from the last failed (not having to start again from the first test). Tried pytest, nose, nose2 but they dont seem to fit this need although one of them had a continue from last failed feature but randomly it would start all again..

What if we can use the tried in true 'GNU Make' as used in most C projects like the Linux kernel sources to run python tests? It can run concurrently like `make -j4`, it continues from last failed just by running `make`, start over with `make clean` then `make` - yes it worked perfectly for my needs! Just make sure you handle `__main__` to run your test function in each `test_<test name>.py` file so that each test could be run with `python test_<test name>.py`.

To use this, just use copy this Makefile from my github repo into your python project folder then run `make`: <https://github.com/ykasidit/python_make_test_example/blob/master/Makefile>