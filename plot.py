#!/usr/bin/env python3

import fileinput

import matplotlib.pyplot as plt
import numpy as np


def get_data():
    for line in fileinput.input(encoding="utf-8"):
        v, e, time = map(int, line.strip().split())
        load = 100 * e / (v * (v - 1) // 2)
        yield [np.log10(v), load, np.log10(time)]


DATA = np.array(list(get_data()))
fig, ax = plt.subplots(subplot_kw={"projection": "3d"})
ax.scatter(*DATA.T)
ax.set_xlabel("Vertices (log10)")
ax.set_ylabel("Edge Load")
ax.set_zlabel("Time (log10)")
plt.show()
