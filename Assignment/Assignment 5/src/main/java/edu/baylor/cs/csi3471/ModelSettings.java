package edu.baylor.cs.csi3471;

import java.util.Objects;

public class ModelSettings {

	public static class MPG {
		private Integer cityMpg = null;
		private Integer avgMpg = null;
		private Integer hwyMpg = null;

		public MPG(String[] line) {
			if (line[0] != null && !line[0].trim().isEmpty()) {
				cityMpg = Integer.parseInt(line[0]);
			}

			if (line[1] != null && !line[1].trim().isEmpty()) {
				avgMpg = Integer.parseInt(line[1]);
			}

			if (line[5] != null && !line[5].trim().isEmpty()) {
				hwyMpg = Integer.parseInt(line[5]);
			}
		}

		@Override
		public String toString() {
			return cityMpg + "/" + avgMpg + "/" + hwyMpg;
		}
	}

	private String makeName = null;
	private MPG mpg = null;
	private Integer cylinders = null;
	private Double displ = null;
	private String fuelType = null;
	private String modelName = null;
	private String trany = null;
	private String vClass = null;
	private Integer year = null;

	public MPG getMpg() {
		return mpg;
	}

    public void setMpg(String[] line) {
        mpg = new edu.baylor.cs.csi3471.ModelSettings.MPG(line);
    }

	public String getModelName() {
		return modelName;
	}

	public Integer getYear() {
		return year;
	}

	public String getVClass() {
		return vClass;
	}

	public Integer getCombinedMpg() {
		return mpg.avgMpg;
	}

	@Override
	public int hashCode() {
		return Objects.hash(makeName, cylinders, displ, fuelType, modelName, trany, vClass, year);
	}

	@Override
	public String toString() {
		return "\n" + modelName + ", " + year + ", " + mpg.toString();
	}

	public ModelSettings(String[] line) {
		this.makeName = line[6];
		this.mpg = new edu.baylor.cs.csi3471.ModelSettings.MPG(line);

		if (line[2] != null && !line[2].trim().isEmpty()) {
			this.cylinders = Integer.parseInt(line[2]);
		}

		if (line[3] != null && !line[3].trim().isEmpty()) {
			this.displ = Double.parseDouble(line[3]);
		}

		this.fuelType = line[4];
		this.modelName = line[7];
		this.trany = line[8];
		this.vClass = line[9];

		if (line[10] != null && !line[10].trim().isEmpty()) {
			this.year = Integer.parseInt(line[10]);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ModelSettings model = (ModelSettings) obj;
		return Objects.equals(makeName, model.makeName) &&
				Objects.equals(cylinders, model.cylinders) &&
				Objects.equals(displ, model.displ) &&
				Objects.equals(fuelType, model.fuelType) &&
				Objects.equals(modelName, model.modelName) &&
				Objects.equals(trany, model.trany) &&
				Objects.equals(vClass, model.vClass) &&
				Objects.equals(year, model.year);
	}
}
