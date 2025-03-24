package edu.baylor.cs.csi3471;

import java.util.*;

public class Make {

	private static Integer uniqueId = 0;
	private String makeName = null;
	private Integer id = null;

	private Set<ModelSettings> modelSettingSet;

	public Set<ModelSettings> getModelSettingSet() {
		return modelSettingSet;
	}

	public void setModelSettingSet(Set<ModelSettings> modelSettingSet) {
		this.modelSettingSet = modelSettingSet;
	}

	public String getMakeName() {
		return makeName;
	}

	public int getModelSettingSetSize() {
		return modelSettingSet.size();
	}

	@Override
	public int hashCode() {
		return Objects.hash(makeName);
	}

	@Override
	public String toString() {
		TreeSet<ModelSettings> sortedModels = new TreeSet<>(new Comparator<ModelSettings>() {
			@Override
			public int compare(ModelSettings ms1, ModelSettings ms2) {
				int nameComparison = ms1.getModelName().compareTo(ms2.getModelName());
				if (nameComparison != 0) {
					return nameComparison;
				}
				return Integer.compare(ms1.getYear(), ms2.getYear());
			}
		});
		sortedModels.addAll(modelSettingSet);
		return makeName + sortedModels.toString();
	}

	public Make(String[] line) {
		this.makeName = line[6];
		this.id = uniqueId;
		modelSettingSet.add(new ModelSettings(line));
		uniqueId++;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Make make = (Make) obj;
		return Objects.equals(makeName, make.makeName);
	}

	// there are 2 options, do this functionality here(its static),
	// or in your tester.java and call this method from the make object that a 
	// line is. I would suggest number 2. 
	public Collection<Make> creatorPattern(String[] line, Collection<Make> makes) {
		if (!modelSettingSet.contains(new ModelSettings(line))) {
			// if the make does not exist then create a new one
		} else {
			// if the make does exist, update its modelSettingSet
		}
		return makes;
	}
}
