package com.qbao.aisr.stuff.main;

public class Price_class {
	public static double mean(double[] a) {
		int n = a.length;
		double s = 0;
		for (int i = 0; i < n; i++) {
			s = s + a[i];
		}
		s = s / n;
		return s;
	}

	public static double var(double[] a, double am) {
		int n = a.length;
		double s = 0;
		for (int i = 0; i < n; i++) {
			s = s + Math.pow(a[i] - am, 2);
		}

		s = Math.sqrt(s) / n;
		return s;
	}

	public static double[] Sort(double[] a) {
		int num = a.length;
		double[] x = new double[num];
		int[] r = new int[num];
		int[][] label = new int[num][2];
		for (int i = 0; i < num; i++) {
			label[i][0] = i;
			label[i][1] = i;
			r[i] = i;
			x[i] = a[i];
		}
		int n = (int) Math.ceil(Math.log10(num) / Math.log10(2));
		int n1 = num;
		int n2;

		for (int i = 0; i < n; i++) {
			n2 = n1 / 2;
			for (int j = 0; j < n2; j++) {
				int[] left = new int[2];
				int[] right = new int[2];
				int m = 2 * j;
				left[0] = label[m][0];
				left[1] = label[m][1];
				m = m + 1;
				right[0] = label[m][0];
				right[1] = label[m][1];
				int nl = left[1] - left[0] + 1;

				int nr = right[1] - right[0] + 1;

				int ns = nl + nr;
				int[] rl = new int[nl];
				int[] rr = new int[nr];
				double[] xl = new double[nl];
				double[] xr = new double[nr];
				for (int k = 0; k < nl; k++) {
					xl[k] = x[left[0] + k];
					rl[k] = r[left[0] + k];

				}
				for (int k = 0; k < nr; k++) {
					xr[k] = x[right[0] + k];
					rr[k] = r[right[0] + k];

				}

				int sl = 0, sr = 0;

				for (int s = 0; s < ns; s++) {
					if (sl == nl) {
						x[left[0] + s] = xr[sr];
						r[left[0] + s] = rr[sr];
						sr = sr + 1;
					} else {
						if (sr == nr) {
							x[left[0] + s] = xl[sl];
							r[left[0] + s] = rl[sl];
							sl = sl + 1;

						} else {
							if (xl[sl] < xr[sr]) {
								x[left[0] + s] = xl[sl];
								r[left[0] + s] = rl[sl];
								sl = sl + 1;
							} else {
								x[left[0] + s] = xr[sr];
								r[left[0] + s] = rr[sr];
								sr = sr + 1;
							}
						}
					}

				}
				label[j][0] = left[0];
				label[j][1] = right[1];
			}
			if (n1 > 2 * n2) {
				label[n2][0] = label[n1 - 1][0];
				label[n2][1] = label[n1 - 1][1];
				n1 = n2 + 1;
			} else {
				n1 = n2;
			}
		}

		return x;
	}

	public static double[] Getlabel(int n, double[] r, double[] a) {
		double[] label = new double[n];
		int al = a.length;
		for (int i = 0; i < n; i++) {
			int location = (int) Math.floor(al * r[i]);
			label[i] = a[location];
		}
		return label;

	}

	public static int[] classify(double[] a, int nc, double[] r) {
		double[] x = Sort(a);
		double[] label = Getlabel(nc, r, x);
		int n = a.length;
		int[] C = new int[n];
		for (int i = 0; i < n; i++) {
			int k = 1;
			for (int j = 0; j < nc; j++) {
				if (a[i] > label[j]) {
					k = k + 1;
				} else {
					break;
				}
			}

			C[i] = k;
		}
		return C;
	}

}
