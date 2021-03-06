package com.zj.study.algorithm.search;

public class SearchMain {

	public static void main(String[] args) {
		// int[] a = new int[] { 4, 3, 5, 7, 9, 2, 1, 6, 8, 99 };
		// SortMain sortMain = new SortMain();
		// sortMain.quickSort(a);
		//
		// System.out.println(Arrays.toString(a));
		// 旋转数组
		int[] b = new int[] { 99, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		SearchMain searchMain = new SearchMain();
		int key = 9;
		// 经典二分查找
		// int index = searchMain.binarySearch(b, 9);
		// System.out.println("key:" + key + " index:" + index);

		// 旋转数组查找
		// System.out.println(searchMain.rotateBinaryMin(b));
		b = new int[] { 1, 4 };
		// System.out.println(searchMain.searchInsertPos(b, 5));
		// System.out.println(Arrays.toString(searchMain.searchRange(b, 2)));
		// System.out.println(searchMain.searchFirst(b, 7));

//		int[] houses = new int[] { 1, 2, 3 };
//		int[] heaters = new int[] { 2 };

//		int[] houses = new int[] { 1, 2, 3, 4 };
//		int[] heaters = new int[] { 1, 4 };

//		int[] houses = new int[] { 1, 12, 23, 34 };
//		int[] heaters = new int[] { 12, 24 };

//		System.out.println(searchMain.findRadius(houses, heaters));
//		System.out.println(searchMain.sqrt(36));

//		int[] c = { 3, 5, 6, 3, 1, 4, 2 };
//		System.out.println(searchMain.findDuplicate(c));

//		int[] d = { 1, 5, 8, 3, 2 };
//		System.out.println(searchMain.searchKMax(d, 2));

		int[] e = { 1, 3, 5, 7, 9, 11, 13 };
		int[] f = { 1, 5, 7, 9, 11, 13 };
		System.out.println(searchMain.find_extra_fast(e, f));
	}

	private int find_extra_fast(int[] a, int[] b) {
		int index = b.length;

		int left = 0;
		int right = b.length - 1;

		while (left <= right) {
			int mid = (left + right) / 2;
			if (b[mid] == a[mid])
				left = mid + 1;
			else {
				index = mid;
				right = mid - 1;
			}
		}

		return index;
	}

	/**
	 * 搜索第K大，采用快速排序的思路
	 * 
	 * @param m
	 * @param k
	 * @return
	 */
	private int searchKMax(int[] a, int k) {
		int start = 0;
		int end = a.length - 1;

		// 进行一轮
		while (start <= end) {
			int mid = a[start];
			int i = start;
			int j = end;
			while (i < j) {
				// 先从右边查找,否则会移动不正确
				for (; i < j; j--) {
					if (a[j] < mid)
						break;
				}

				for (; i < j; i++) {
					if (a[i] > mid)
						break;
				}

				if (i < j) {
					int t = a[i];
					a[i] = a[j];
					a[j] = t;
				}
			}

			a[start] = a[j];
			a[j] = mid;

			if (j == k)
				return mid;
			else if (j > k) {
				end = j - 1;
			} else {
				start = j + 1;
			}
		}
		return -1;
	}

	/**
	 * 查找重复 给定一个包含n + 1个整数的数组，其中每个整数在1到n（包括1和n）之间，请证明必须存在至少一个重复的数字。
	 * 假定只有一个重复的数字，找到重复的一个。
	 * 
	 * @param m
	 * @return
	 */
	private int findDuplicate(int[] m) {
		int low = 1;
		int high = m.length - 1;

		while (low < high) {
			int mid = low + (high - low) / 2;
			int count = 0;
			for (int num : m)
				if (num <= mid)
					count += 1;
			if (count <= mid)
				low = mid + 1;
			else
				high = mid;
		}
		return low;
	}

	private int sqrt(int x) {
		if (x == 0)
			return 0;

		int low = 0;
		int high = x;

		while (low <= high) {
			int mid = low + (high - low) / 2;

			int tmp = x / mid;
			if (mid == tmp)
				return mid;

			if (mid < tmp)
				low = mid + 1;
			else
				high = mid - 1;
		}

		return high;
	}

	/**
	 * 寻找管道
	 * 
	 * @param houses
	 * @param heaters
	 * @return
	 */
	private int findRadius(int[] houses, int[] heaters) {
		int ans = 0;
		for (int hourse : houses) {
			// 寻找插入位置
			int pos = searchInsertPos(heaters, hourse);
			int left = Integer.MAX_VALUE;
			int right = Integer.MAX_VALUE;
			if (pos < heaters.length) {
				if (heaters[pos] == hourse) {
					left = 0;
					right = 0;
				} else {
					if (pos == 0)
						left = Integer.MAX_VALUE;
					else
						left = hourse - heaters[pos - 1];

					right = heaters[pos] - hourse;
				}
			} else {
				left = hourse - heaters[pos - 1];
				right = Integer.MAX_VALUE;
			}
//			System.out.println("left:" + left + " right:" + right);
			ans = Math.max(ans, Math.min(left, right));
//			System.out.println("ans:" + ans);
		}

		return ans;
	}

	/**
	 * 
	 * 在流中搜索一个元素
	 * 
	 * @param as
	 * @param key
	 * @return
	 */
	public int searchFirst(int[] as, int key) {
		int length = as.length;

		int left = 0;
		int right = 1;

		while (right < length) {
			if (as[right] < key) {
				left = right;
				right = 2 * right;
			} else
				break;

			if (right > length) {
				right = length - 1;
				break;
			}
		}

		if (right < length) {
			int b[] = new int[right - left + 1];
			System.arraycopy(as, left, b, 0, right - left + 1);
			int pos = searchRange(b, key)[0];
			if (pos != -1)
				return left + pos;
		}
		return -1;
	}

	/**
	 * 
	 * 寻找一个数的起止范围
	 * 
	 * @param a
	 * @param key
	 * @return
	 */
	public int[] searchRange(int[] a, int key) {
		// search left
		int low = 0;
		int high = a.length - 1;
		// 这里要+1，因为里面没有对mid进行加1,寻找最小的值
		while (low + 1 < high) {
			int mid = (low + high) >>> 1;

			if (a[mid] == key) {
				high = mid;
			} else if (a[mid] < key) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}

		int lpos;
		if (a[low] == key) {
			lpos = low;
		} else if (a[high] == key) {
			lpos = high;
		} else {
			return new int[] { -1, -1 };
		}

		low = 0;
		high = a.length - 1;
		while (low + 1 < high) {
			int mid = (low + high) >>> 1;

			if (a[mid] == key) {
				low = mid;
			} else if (a[mid] < key) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}

		int rpos;
		if (a[high] == key) {
			rpos = high;
		} else if (a[low] == key) {
			rpos = low;
		} else {
			return new int[] { -1, -1 };
		}

		return new int[] { lpos, rpos };
	}

	// 寻找插入位置
	public int searchInsertPos(int[] a, int key) {
		int low = 0;
		int high = a.length - 1;
		while (low < high - 1) {
			int mid = (low + high) >>> 1;
			if (key < a[mid]) {
				high = mid - 1;
			} else if (key > a[mid]) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		if (a[low] >= key)
			return low;

		if (a[high] >= key)
			return high;

		return high + 1;
	}

	/**
	 * 
	 * 旋转数组查找最小值
	 * 
	 * @param a
	 * @param key
	 * @return
	 */
	// { 5, 6, 7, 8, 9, 99,1, 2, 3, 4 };
	public int rotateBinarySearch(int[] a, int key) {
		int low = 0;
		int high = a.length - 1;
		// 这里要+1，因为里面没有对mid进行加1
		while (low < high) {
			int mid = (low + high) >>> 1;

			if (a[mid] == key)
				return mid;

			// 判断是否是排好序的
			if (a[low] < a[mid]) {
				if (a[low] <= key && key < a[mid])
					high = mid - 1;
				else
					low = mid + 1;
			} else {
				if (a[low] < key && key > a[mid])
					high = mid - 1;
				else
					low = mid + 1;
			}
		}

		System.out.println("low:" + low + " high:" + high);
		if (a[low] == key)
			return low;

		if (a[high] == high)
			return high;
		return -1;
	}

	/**
	 * 
	 * 旋转数组查找最小值
	 * 
	 * @param a
	 * @param key
	 * @return
	 */
	// { 5, 6, 7, 8, 9, 99,1, 2, 3, 4 };
	public int rotateBinaryMin(int[] a) {
		int low = 0;
		int high = a.length - 1;
		// 这里要+1，因为里面没有对mid进行加1
		while (low + 1 < high) {
			// 判断是否是排好序的
			if (a[low] < a[high])
				return a[low];

			int mid = (low + high) >>> 1;

			if (a[mid] >= a[low])
				low = mid + 1;
			else
				high = mid;
		}

		return a[low] < a[high] ? a[low] : a[high];
	}

	/**
	 * 二分搜索模板
	 * 
	 * @param a
	 * @param key
	 * @return
	 */
	public int binarySearch(int[] a, int key) {
		int low = 0;
		int high = a.length - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			if (key < a[mid]) {
				high = mid - 1;
			} else if (key > a[mid]) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
}
