package mini3;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.*;

public class Combinations {
	public static int[][] getCombinations(int[] choices) {
		if (choices.length == 1) {
			return IntStream.range(0, choices[0]).mapToObj(i -> {
				return new int[] { i };
			}).toArray(int[][]::new);
		}

		int[][] a = new int[choices.length][];
		for (int i = 0; i < choices.length; i++) {
			a[i] = Arrays.stream(getCombinations(Arrays.copyOfRange(choices, i, i + 1))).mapToInt(s -> s[0]).toArray();
		}
		
		System.out.println(Arrays.deepToString(a));

		ArrayList<ArrayList<Integer>> o = new ArrayList<ArrayList<Integer>>(a[0].length);

		for (int i = 0; i < a[0].length; i++) {
			o.add(new ArrayList<Integer>());
			o.get(o.size() - 1).add(a[0][i]);
		}

		for (int i = 1; i < a.length; i++) {
			if (a[i].length == 1) {
				for (ArrayList<Integer> p : o) {
					p.add(a[i][0]);
				}

				continue;
			}

			ArrayList<ArrayList<Integer>> k = new ArrayList<>(
					o.stream().map(x -> new ArrayList<>(x)).collect(Collectors.toList()));

			for (int j = 0; j < k.size(); j++) {
				for (int y = 0; y < a[i].length; y++) {
					if (y != a[i].length - 1) {
						o.add(o.lastIndexOf(k.get(j)), new ArrayList<Integer>(k.get(j)));
					}
				}
			}

			for (int j = 0; j < o.size(); j++) {
				o.get(j).add(a[i][j % a[i].length]);
			}
		}

		int[][] k = new int[o.size()][];

		for (int i = 0; i < o.size(); i++) {
			ArrayList<Integer> r = o.get(i);

			k[i] = r.stream().mapToInt(s -> s).toArray();
		}

		return k;
	}
}
