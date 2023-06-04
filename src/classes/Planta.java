package classes;

public class Planta extends SerVivo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float fRiego;
	private String location;
	
	//--- Constructors --------------------------------
	public Planta() {
        super();
    }

	// --- Getters and Setters --------------------------------
	public float getfRiego() {
		return fRiego;
	}

	public void setfRiego(float fRiego) {
		this.fRiego = fRiego;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public void getDatos() {
		super.getDatos();
        System.out.println("Riego: " + fRiego);
        System.out.println("Location: " + location);
	}
}
