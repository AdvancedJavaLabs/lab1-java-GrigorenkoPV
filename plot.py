with open("tmp/times.txt") as f:
    data = f.read()
data = [list(map(int, line.split())) for line in data.splitlines()]

for v, e, time in data:
    load = 100 * e / (v * (v - 1) // 2)
    print(f"{{{v}, {load}, {time}}},")