package edu.baylor.cs.csi3471;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Tester {

	private static final int FILE_NAME = 1;
	private static final int OPTION = 0;

	private static int readOption(String[] args) {
		Integer option = null;
		if (args.length != 2 && args.length != 4) {
			System.err.println("USAGE: java Tester <filename>");
			System.exit(1);
		} else {
			try {
				option = Integer.parseInt(args[OPTION]);
			} catch (NumberFormatException e) {
				System.err.println("call as java Tester <filename>");
				System.exit(1);
			}
		}
		return option;
	}

	/*
	 * public static Collection<Make> populateSet(Collection<Make> set, String[]
	 * line){ //check the colleciton for existing make }
	 */

	private static Set<Make> loadCSV(String file) throws FileNotFoundException {
		BufferedReader reader = null;
		Set<Make> makes = new HashSet<>();
		try {
			// ok, this is much faster than scanner :)
			reader = new BufferedReader(new FileReader(new File(file)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] split = line.split(",");
				boolean makeExists = false;
				Make newMake = new Make(split);
				Make existingMake = null;
				ModelSettings newModelSettings = new ModelSettings(split);
				for (Make findMake : makes) {
					if (findMake.hashCode() == newMake.hashCode()) {
						makeExists = true;
						existingMake = findMake;
					}
				}
				if (makeExists) {
					if (!existingMake.getModelSettingSet().contains(newModelSettings)) {
						existingMake.getModelSettingSet().add(newModelSettings);
					}
					else {
						for (ModelSettings findModelSettings : existingMake.getModelSettingSet()) {
							if (findModelSettings.hashCode() == newModelSettings.hashCode()) {
								findModelSettings.setMpg(split);
							}
						}
					}
				}
				else {
					makes.add(newMake);
				}
			}
			return makes;
		} catch (IOException e) {
			String hint = "";
			try {
				hint = "Current dir is: " + new File(".").getCanonicalPath();
			} catch (Exception local) {
				hint = local.getLocalizedMessage();
			}
			throw new FileNotFoundException(e.getLocalizedMessage() + "\n" + hint);

		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		}

	}

	private static void OptionOne(Set<Make> makes) {
		TreeSet<Make> sortedSet = new TreeSet<>(new Comparator<Make>() {
			@Override
			public int compare(Make m1, Make m2) {
				// sort by makeName reversed alphabetically (swap m1 and m2 for alphabetical)
				return m2.getMakeName().compareTo(m1.getMakeName());
			}
		});
		sortedSet.addAll(makes);

		System.out.println("Number of Makes: " + makes.size());
		System.out.println("===============");
		System.out.println("Make Name -- Number of Model Settings");
		for (Make printMakes : sortedSet) {
			System.out.println(printMakes.getMakeName() + " -- " + printMakes.getModelSettingSetSize());
		}
		System.out.println("===============");
		for (Make printMakes : sortedSet) {
			System.out.println(printMakes.toString() + "\n");
		}
	}

	private static void OptionTwo(Set<Make> makes, String[] args) {
		List<Make> filteredMakes = new ArrayList<>();

		for (Make make : makes) {
			boolean matches = false;

			if ("makeName".equalsIgnoreCase(args[2])) {
				// check if makeName contains any part of the value
				if (make.getMakeName().toLowerCase().contains(args[3].toLowerCase())) {
					matches = true;
				}
			} else if ("model".equalsIgnoreCase(args[2])) {
				// check if any model within ModelSettings contains the value
				for (ModelSettings model : make.getModelSettingSet()) {
					if (model.getModelName().toLowerCase().contains(args[3].toLowerCase())) {
						matches = true;
						break; // stop checking if at least one model matches
					}
				}
			}

			if (matches) {
				filteredMakes.add(make);
			}
		}

		// sort results list by makeName A->Z and print
		filteredMakes.sort(Comparator.comparing(Make::getMakeName));
		for (Make make : filteredMakes) {
			System.out.println(make.getMakeName());
		}
	}

	private static void OptionThree(Set<Make> makes) {
		Map<String, List<Integer>> vClassToMpg = new HashMap<>();

		// group MPG values by vClass
		for (Make make : makes) {
			for (ModelSettings model : make.getModelSettingSet()) {
				String vClass = model.getVClass();  // get the vClass
				Integer combinedMpg = model.getCombinedMpg(); // get the combined mpg (avg)

				vClassToMpg.computeIfAbsent(vClass, k -> new ArrayList<>()).add(combinedMpg);
			}
		}

		// calculate average MPG for each VClass
		Map<String, Double> vClassAvgMPG = new HashMap<>();
		for (Map.Entry<String, List<Integer>> entry : vClassToMpg.entrySet()) {
			String vClass = entry.getKey();
			List<Integer> mpgValues = entry.getValue();
			double averageMPG = mpgValues.stream().mapToInt(Integer::intValue).average().orElse(0.0);
			vClassAvgMPG.put(vClass, averageMPG);
		}

		// sort by vClass name (A-Z) and print results
		vClassAvgMPG.entrySet().stream()
				.sorted(Map.Entry.comparingByKey()) // Sort by VClass alphabetically
				.forEach(entry -> System.out.println(entry.getKey() + ": " + String.format("%.2f", entry.getValue()) + " MPG"));
	}

	private static void OptionFour(Set<Make> makes) {
		Map<String, Map<Integer, Integer>> makeYearCounts = new HashMap<>();

		// group model counts per year for each make
		for (Make make : makes) {
			String makeName = make.getMakeName(); // get makeName

			// if make not in map, add
			makeYearCounts.putIfAbsent(makeName, new HashMap<>());

			for (ModelSettings model : make.getModelSettingSet()) {
				int year = model.getYear(); // get year from model settings

				// use sub-map of year and number of models to sum models from year from make
				makeYearCounts.get(makeName).merge(year, 1, Integer::sum);
			}
		}

		// sort by makeName (A-Z) and print results
		makeYearCounts.entrySet().stream()
				.sorted(Map.Entry.comparingByKey()) // sort alphabetically
				.forEach(entry -> {
					String makeName = entry.getKey();
					entry.getValue().entrySet().stream()
							.sorted(Map.Entry.comparingByKey()) // sort years in ascending order
							.forEach(yearEntry ->
									System.out.println(makeName + ", " + yearEntry.getKey() + ", " + yearEntry.getValue())
							);
				});
	}

	public static void main(String[] args) {
		int option = readOption(args);

		Set<Make> makes = null;
		try {
			makes = loadCSV(args[FILE_NAME]);
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

		if (option == 1)
			OptionOne(makes);
		else if (option == 2)
			OptionTwo(makes, args);
		else if (option == 3)
			OptionThree(makes);
		else if (option == 4)
			OptionFour(makes);
		else {
			System.err.println("Call as: java Tester <1-4> <fileName>");
			System.exit(1);
		}
	}
}
