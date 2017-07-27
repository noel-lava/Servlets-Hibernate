package com.jlava.apputil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang3.*;

public class AppUtil{
	static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public static void println(String line) {
        System.out.println(line);
    }

    public static void print(String line) {
    	System.out.print(line);
    }
    
    public static int acceptValidInt(int min) {
        Scanner scanner = new Scanner(System.in);
		boolean validInput = false;
		int input = 0;
		
		while(!validInput) {
			try{
				input = scanner.nextInt();
				
				if(input < min) {
					print("\n[Invalid input, try again] : ");
					validInput = false;
				} else {
					validInput = true;
				}
			}catch(InputMismatchException ime) {
				print("\n[Invalid input, try again] : ");
				scanner = new Scanner(System.in);
			}
		}
		
		return input;
	}

	public static int updateValidInt(int value, int min) {
		Scanner scanner = new Scanner(System.in);
		int result = 0;
		String input = scanner.nextLine();

		if(StringUtils.isEmpty(input)) {
			return value;
		} else {
			try {
				result = Integer.parseInt(input);

				if(result < min) {
					throw new NumberFormatException();
				} else {
					return result;
				}
			} catch (NumberFormatException nfe) {
				print("\n[Invalid input, try again] : ");
				return updateValidInt(value, min);
			}
		}
	}

	public static int updateValidInt(int value, int min, int max) {
		Scanner scanner = new Scanner(System.in);
		int result = 0;
		String input = scanner.nextLine();

		if(StringUtils.isEmpty(input)) {
			return value;
		} else {
			try {
				result = Integer.parseInt(input);

				if(result < min || result > max) {
					throw new NumberFormatException();
				} else {
					return result;
				}
			} catch (NumberFormatException nfe) {
				print("\n[Invalid input, try again] : ");
				return updateValidInt(value, min);
			}
		}
	}
    
    public static int acceptValidInt(int min, int max) {
        Scanner scanner = new Scanner(System.in);
		boolean validInput = false;
		int input = 0;
		
		while(!validInput) {
			try {
				input = scanner.nextInt();
				
				if(input < min || input > max) {
					print("\n[Invalid input, try again] : ");
					validInput = false;
				} else {
					validInput = true;
				}
			}catch(InputMismatchException ime) {
				print("\n[Invalid input, try again] : ");
				scanner = new Scanner(System.in);
			}
		}
		
		return input;
	}

	public static float readFloat(float min, float max) {
        Scanner scanner = new Scanner(System.in);

		boolean validInput = false;
		float input = 0;

		while(!validInput) {
			try {
				input = scanner.nextFloat();

				if(input < min || input > max) {
					print("\n[Invalid input, try again] : ");
					validInput = false;
				}else {
					validInput = true;
				}
			} catch(InputMismatchException ime) {
				print("\n[Invalid input, try again] : ");
				scanner = new Scanner(System.in);
			}
		}

		return input;
	}

	public static float readFloat(float value, float min, float max) {
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		float fp = 0;

		if(StringUtils.isEmpty(input)) {
			return value;
		} else {
			try {
				fp = Float.parseFloat(input);

				if(fp < min || fp > max) {
					//print("\n[Invalid input, try again] : ");
					throw new NumberFormatException();
				}

				return fp;
			} catch(NumberFormatException nfe) {
				print("\n[Invalid input, try again] : ");
				return readFloat(value, min, max);
			}
		}
	}
    
    public static String readLine(boolean acceptNull, int length) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
     
        if(acceptNull && StringUtils.isEmpty(input)) {
        	return null;
        } else if(StringUtils.isEmpty(input)) {
            print("\n[Invalid input, please try again] : ");
        	return readLine(acceptNull, length);
        } else if(input.length() > length) {
			print("[Input too long - Max input for this field is " + length + "] : ");
			return readLine(acceptNull, length);
        } else {
        	return input;
        }
    }

    public static String readLine(String value, boolean acceptNull, int length) {
    	Scanner scanner = new Scanner(System.in);
    	String input = scanner.nextLine();

    	if(StringUtils.isEmpty(input)) {
    		return value;
    	} else if (input.equals("null") && acceptNull) {
    		return null;
    	} else {
    		if(input.length() > length) {
    			print("[Input too long - Max input for this field is " + length + "] : ");
    			return readLine(value, acceptNull, length);
    		}
    		return input;
    	}

    }
    
    public static String readNumeric(boolean acceptNull, int length) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
     
        if(acceptNull && StringUtils.isEmpty(input)) {
        	return null;
        } else if(StringUtils.isEmpty(input) || !StringUtils.isNumeric(input)) {
            print("\n[Invalid input, please try again] : ");
            return readNumeric(acceptNull, length);
        } else if(input.length() > length) {
    		print("[Input too long - Max input for this field is " + length + "] : ");		
    		return readNumeric(acceptNull, length);
        } else {
        	return input;
        }
    }

    public static String readNumeric(String value, boolean acceptNull, int length) {
    	Scanner scanner = new Scanner(System.in);
    	String input = scanner.nextLine();

    	if(StringUtils.isEmpty(input)) {
    		return value;
    	} else if (input.equals("null") && acceptNull) {
    		return null;
    	} else if(!StringUtils.isNumeric(input)) {
            print("\n[Invalid input, please try again] : ");
			return readNumeric(value, acceptNull, length);
		} else if(input.length() > length) {
    		print("[Input too long - Max input for this field is " + length + "] : ");		
    		return readNumeric(value, acceptNull, length);
        } else {
    		return input;		
		}
    }

    public static boolean readBool() {
        Scanner scanner = new Scanner(System.in);
    	String input = scanner.nextLine().toLowerCase();
    	boolean valid = (input.equals("y") || input.equals("n"))? true : false;

    	while (!valid) {
    		print("\n[Invalid input, please try again] : ");
    		input = scanner.nextLine().toLowerCase();
    		valid = (input.equals("y") || input.equals("n"))? true : false;
    	}

    	boolean bool = (input.equals("y"))?true:false;

    	return bool;
    }

    public static boolean readBool(boolean value) {
    	Scanner scanner = new Scanner(System.in);
    	String input = scanner.nextLine().toLowerCase();
    	boolean valid = (input.equals("y") || input.equals("n"))? true : false;

    	if(StringUtils.isEmpty(input)) {
    		return value;
    	} else {
    		while (!valid) {
	    		print("\n[Invalid input, please try again] : ");
	    		input = scanner.nextLine().toLowerCase();
	    		valid = (input.equals("y") || input.equals("n"))? true : false;

	    		if(StringUtils.isEmpty(input)) {
	    			return value;
	    		}
	    	}

	    	return (input.equals("y"))?true:false;
    	}
    }

    static Date readDate(String date) {
    	try {
    		Date result = dateFormat.parse(date);
    		return result;
    	} catch (Exception e) {
    		return null;
    	}
    }

    public static Date readDate(boolean acceptNull) {
        Scanner scanner = new Scanner(System.in);
    	Date date = null;
    	Date today = new Date();

    	while(date == null) {
    		String input = scanner.nextLine();
    		try {
    			if(StringUtils.isEmpty(input) && acceptNull) {
    				return null;
    			}

    			date = dateFormat.parse(input);

    			if(date.after(today)) {
    				print("\n[Invalid Date - date can not be later than today] : ");
    				date = null;
    			}
    		} catch (Exception e) {
    			print("\n[Invalid input, please try again] : ");
    			if(StringUtils.isEmpty(input) && acceptNull) {
    				return null;
    			}
    		}
    	}

    	return date;
    }

    public static Date readDate(Date value, boolean acceptNull) {
    	Scanner scanner = new Scanner(System.in);
    	String input = scanner.nextLine();
    	Date date = readDate(input);
    	Date today = new Date();

    	if(StringUtils.isEmpty(input)) {
    		return value;
    	} else if(input.equals("null") && acceptNull) {
    		return null;
    	} else if(date != null) {
			if(date.after(today)) {
				print("\n[Invalid Date - date can not be later than today] : ");
				return readDate(value, acceptNull);
			}
    		return date;
    	} else {
			print("\n[Invalid input, please try again] : ");
			return readDate(value, acceptNull);
    	}
    }
}
