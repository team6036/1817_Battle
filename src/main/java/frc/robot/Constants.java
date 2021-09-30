package frc.robot;

import frc.robot.math.Util;

public class Constants {

    /** ////////////////////////////////////////////
     * REAL PHYSICAL CONSTANTS (meters, kilograms, seconds, Newtons, radians)
     * //////////////////////////////////////////// */ 
    public static Constant MAX_VOLTAGE = new Constant("MAX_VOLTAGE", 12, Type.DOUBLE);

    public static Constant ROBOT_MASS = new Constant("ROBOT_MASS", 45, Type.DOUBLE); //about 100 lbs
    public static Constant WHEEL_RADIUS = new Constant("WHEEL_RADIUS", Util.inchesToMeters(2), Type.DOUBLE);
    public static Constant WHEEL_XDIST = new Constant("WHEEL_XDIST", Util.inchesToMeters(12), Type.DOUBLE);
    public static Constant WHEEL_YDIST = new Constant("WHEEL_YDIST", Util.inchesToMeters(11), Type.DOUBLE);

    public static Constant SWERVE_MOI = new Constant("SWERVE_MOI", 0.1, Type.DOUBLE);


    /** ////////////////////////////////
     * SIMULATOR CONFIG
     * //////////////////////////////// */  
    public static Constant CONTROLLER_INDEX = new Constant("Controller_INDEX", 0, Type.INT); //which joystick?
    public static Constant DISPLAY_SCALE = new Constant("DISPLAY_SCALE", 140, Type.DOUBLE); //in pixels per meter
    public static Constant PHYSICS_DT = new Constant("PHYSICS_DT", 0.001, Type.DOUBLE); //in seconds per update
    public static Constant USERCODE_DT = new Constant("USERCODE_DT", 0.02, Type.DOUBLE); //in seconds per update
    public static Constant DISPLAY_DT = new Constant("DISPLAY_DT", 0.015, Type.DOUBLE); //frames per second for display updates
    public static Constant SIMSPEED = new Constant("SIMSPEED", 1, Type.DOUBLE); //simulator speed (1 being normal, 2 being fast)

    /** ////////////////////////////////
     * INITIAL CONDITIONS
     * //////////////////////////////// */  
    public static Constant FIELD = new Constant("FIELD", "lightspeed", Type.STRING); //what field image to use
    public static Constant INITX = new Constant("INITX", -3.4, Type.DOUBLE); 
    public static Constant INITY = new Constant("INITY", 0, Type.DOUBLE); 
    public static Constant INITANG = new Constant("INITANG", 0, Type.DOUBLE);

    public static Constant WHEELANG0 = new Constant("WHEELANG0", Math.toRadians(0), Type.DOUBLE);
    public static Constant WHEELANG1 = new Constant("WHEELANG1", Math.toRadians(0), Type.DOUBLE);
    public static Constant WHEELANG2 = new Constant("WHEELANG2", Math.toRadians(0), Type.DOUBLE);
    public static Constant WHEELANG3 = new Constant("WHEELANG3", Math.toRadians(0), Type.DOUBLE);

    /** ////////////////////////////////
     * USERCODE
     * //////////////////////////////// */  
     public static Constant DEMOCONSTANT = new Constant("DEMOCONSTANT", 6, Type.DOUBLE);

    /** ////////////////////////////////
     * ADD CONSTANTS TO THIS LIST TO BE EDITABLE
     * //////////////////////////////// */  
    public static Constant[] constants = {
        FIELD,
        INITX,
        INITY,
        INITANG,
        WHEELANG0,
        WHEELANG1,
        WHEELANG2,
        WHEELANG3,
        DISPLAY_SCALE,
        SIMSPEED,
        PHYSICS_DT,
        USERCODE_DT,
        DISPLAY_DT,
        ROBOT_MASS,
        SWERVE_MOI,
        WHEEL_XDIST,
        WHEEL_YDIST,
        DEMOCONSTANT,
    };

    enum Type {
        BOOLEAN, INT, DOUBLE, STRING;
    }


    public static class Constant {
        private String name;
        private Object value;
        private String defaultValue;
        
        Type type;


        Constant(String name_input, Object value_input, Type type_input){
            name = name_input;
            value = value_input;
            defaultValue = String.valueOf(value_input);
            type = type_input;
        }
        
        public String getName(){
            return name;
        }

        public double getDouble() {
            return Double.valueOf(getString());
        }

        public int getInt() {
            return Integer.valueOf(getString());
        }

        public String getString() {
            return String.valueOf(value);
        }

        public String getDefaultString() {
            return String.valueOf(defaultValue);
        }

        public Object getObject() {
            return value;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public void setValue(Object value_input) {
            this.value = value_input;
        }

    }







    public static class SwerveConstants {
        public static final double DRIVE_RATIO = 6.86;
        public static final double WHEEL_RADIUS = Util.inchesToMeters(2);
        public static final double offsetX = Util.inchesToMeters(26 / 2);
        public static final double offsetY = Util.inchesToMeters(24 / 2);

        public static class FR {
            public static final int D = 4;
            public static final int T = 3;
            public static final int E = 10;
            public static final double offset = Util.toEncoderTicks(275);
        }

        public static final class FL {
            public static final int D = 6;
            public static final int T = 5;
            public static final int E = 11;
            public static final double offset = Util.toEncoderTicks(215);
        }

        public static final class BR {
            public static final int D = 2;
            public static final int T = 1;
            public static final int E = 9;
            public static final double offset = Util.toEncoderTicks(195);
        }

        public static final class BL {
            public static final int D = 8;
            public static final int T = 7;
            public final static int E = 12;
            public final static double offset = Util.toEncoderTicks(190);
        }
    }

}