import java.util.Locale;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.File;
import java.util.AbstractCollection;
import java.io.Writer;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.HashSet;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.StringTokenizer;
import java.io.InputStream;

public class Main {
	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		InputStream inputStream;
		try {
			final String regex = "D-(small|large).*[.]in";
			File directory = new File(".");
			File[] candidates = directory.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.matches(regex);
				}
			});
			File toRun = null;
			for (File candidate : candidates) {
				if (toRun == null || candidate.lastModified() > toRun.lastModified())
					toRun = candidate;
			}
			inputStream = new FileInputStream(toRun);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream("d.out");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		InputReader in = new InputReader(inputStream);
		OutputWriter out = new OutputWriter(outputStream);
		TaskD solver = new TaskD();
		int testCount = Integer.parseInt(in.next());
		for (int i = 1; i <= testCount; i++)
			solver.solve(i, in, out);
		out.close();
	}
}

class TaskD {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
	    int n = in.nextInt();
	    int m = in.nextInt();
	    String[] l1 = new String[n];
	    HashSet<String>[] al = new HashSet[n];
	    for (int i = 0 ; i < n; i++){
		    l1[i] = in.next();
		    al[i] = new HashSet<String>();
		    for (int j = 0;j < l1[i].length(); j++){
			    al[i].add(l1[i].substring(0, j + 1));
		    }
	    }

	    max = Long.MIN_VALUE;
	    patternSet.clear();
	    count = 0;

	    bf(0,new int[n], al, m);
	    System.out.println();
	    out.printLine("Case #" + testNumber + ": " + (max + m) + " " + count );
    }
	public class HashedArray {
		int[] l1 = null;

		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof HashedArray)) return false;
			HashedArray that = (HashedArray) o;
			if (!Arrays.equals(l1, that.l1)) return false;
			return true;
		}

		public int hashCode() {
			return Arrays.hashCode(l1);
		}
	}
	public HashSet<HashedArray> patternSet = new HashSet<HashedArray>();
	public long max = 0;
	public int count = 0;
	public HashSet<String> tempSet = new HashSet<String>();
	public void bf(int idx, int[] noList, HashSet<String>[] al, int m){
		if (idx == noList.length){
			boolean[] hit = new boolean[m];
			for (int e: noList) hit[e] = true;
			for (boolean e: hit) if (!e) return;

			long fin = 0;

			for (int i = 0 ; i< m; i++){
				tempSet.clear();
				for (int j = 0 ; j< noList.length; j++){
					if (noList[j] == i){
						tempSet.addAll(al[j]);
					}
				}
				fin += tempSet.size();
			}
			if (fin > max){
				max = fin;
				count = 1;
			}
			else if (fin == max){
				count++;
			}
//			max = Math.max(max, fin);

//			HashedArray pattern = new HashedArray();
//			pattern.l1 = noList.clone();
//			patternSet.add(pattern);
//			System.out.println(Arrays.toString(pattern.l1));
			return;
		}
		for (int i = 0 ; i < m; i++){
			noList[idx] = i;
			bf(idx + 1, noList, al, m);
		}
	}
}

class InputReader {
	private BufferedReader in = null;
	private StringTokenizer st = null;

	public InputReader(InputStream usingStream) {
		in = new BufferedReader(new InputStreamReader(usingStream));

	}
	public String next(){
		for (;st == null || !st.hasMoreTokens();){
			String currentLine = null;
			try {
				currentLine = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (currentLine == null) return null;
			st = new StringTokenizer(currentLine);
		}
		return st.nextToken();
	}
	public int nextInt(){
		return Integer.parseInt(next());
	}
}

class OutputWriter {
	private PrintWriter usingPrintWriter = null;

	public OutputWriter(OutputStream usingStream){
		usingPrintWriter = new PrintWriter(usingStream);
	}
	public OutputWriter(Writer usingWriter){
		usingPrintWriter = new PrintWriter(usingWriter);
	}

	public void printLine(Object... l1){
		for (int i = 0;i < l1.length; i++){
			if (i != 0) usingPrintWriter.print(" ");
			usingPrintWriter.print(l1[i]);
		}
		usingPrintWriter.println();
	}
	public void flush(){
		usingPrintWriter.flush();
	}
	public void close(){
		flush();
		usingPrintWriter.close();
	}
}

