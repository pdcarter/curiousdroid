package edu.neu.ccs.curiousdroid;

import java.util.Random;

public class PressGenerator {
	// No constructor needed.  Will contain only static methods.
	
	private static int id = 0;
	private static final int PAUSE = 400000; //Changed from 400000 on 4/7/14 for coverage increase attempt

	public static int Ax = 0;
	public static int Ay = 0;
	public static int Bx = 0;
	public static int By = 0;
	public static int Cx = 0;
	public static int Cy = 0;
	public static int Dx = 0;
	public static int Dy = 0;
	public static int Ex = 0;
	public static int Ey = 0;
	public static int Fx = 0;
	public static int Fy = 0;
	public static int Gx = 0;
	public static int Gy = 0;
	public static int Hx = 0;
	public static int Hy = 0;
	public static int Ix = 0;
	public static int Iy = 0;
	public static int Jx = 0;
	public static int Jy = 0;
	public static int Kx = 0;
	public static int Ky = 0;
	public static int Lx = 0;
	public static int Ly = 0;
	public static int Mx = 0;
	public static int My = 0;
	public static int Nx = 0;
	public static int Ny = 0;
	public static int Ox = 0;
	public static int Oy = 0;
	public static int Px = 0;
	public static int Py = 0;
	public static int Qx = 0;
	public static int Qy = 0;
	public static int Rx = 0;
	public static int Ry = 0;
	public static int Sx = 0;
	public static int Sy = 0;
	public static int Tx = 0;
	public static int Ty = 0;
	public static int Ux = 0;
	public static int Uy = 0;
	public static int Vx = 0;
	public static int Vy = 0;
	public static int Wx = 0;
	public static int Wy = 0;
	public static int Xx = 0;
	public static int Xy = 0;
	public static int Yx = 0;
	public static int Yy = 0;
	public static int Zx = 0;
	public static int Zy = 0;
	public static int SHIFTx = 0;
	public static int SHIFTy = 0;
	public static int SYMx = 0;
	public static int SYMy = 0;
	public static int ONEx = 0;
	public static int ONEy = 0;
	public static int TWOx = 0;
	public static int TWOy = 0;
	public static int THREEx = 0;
	public static int THREEy = 0;
	public static int FOURx = 0;
	public static int FOURy = 0;
	public static int FIVEx = 0;
	public static int FIVEy = 0;
	public static int SIXx = 0;
	public static int SIXy = 0;
	public static int SEVENx = 0;
	public static int SEVENy = 0;
	public static int EIGHTx = 0;
	public static int EIGHTy = 0;
	public static int NINEx = 0;
	public static int NINEy = 0;
	public static int ZEROx = 0;
	public static int ZEROy = 0;
	public static int ATx = 0;
	public static int ATy = 0;
	public static int POUNDx = 0;
	public static int POUNDy = 0;
	public static int DOLLARx = 0;
	public static int DOLLARy = 0;
	public static int PERCENTx = 0;
	public static int PERCENTy = 0;
	public static int AMPx = 0;
	public static int AMPy = 0;
	public static int STARx = 0;
	public static int STARy = 0;
	public static int MINUSx = 0;
	public static int MINUSy = 0;
	public static int PLUSx = 0;
	public static int PLUSy = 0;
	public static int OPEN_PARx = 0;
	public static int OPEN_PARy = 0;
	public static int CLOSE_PARx = 0;
	public static int CLOSE_PARy = 0;
	public static int BANGx = 0;
	public static int BANGy = 0;
	public static int DOUBLEx = 0;
	public static int DOUBLEy = 0;
	public static int SINGLEx = 0;
	public static int SINGLEy = 0;
	public static int COLONx = 0;
	public static int COLONy = 0;
	public static int SEMIx = 0;
	public static int SEMIy = 0;
	public static int SLASHx = 0;
	public static int SLASHy = 0;
	public static int QUESTIONx = 0;
	public static int QUESTIONy = 0;
	public static int COMMAx = 0;
	public static int COMMAy = 0;
	public static int PERIODx = 0;
	public static int PERIODy = 0;
	public static int SPACEx = 0;
	public static int SPACEy = 0;
	public static int NEXTx = 0;
	public static int NEXTy = 0;
	
	public static int BACKSPACEx = 0;
	public static int BACKSPACEy = 0;

	public static int NUM_1x = 0;
	public static int NUM_1y = 0;
	public static int NUM_2x = 0;
	public static int NUM_2y = 0;
	public static int NUM_3x = 0;
	public static int NUM_3y = 0;
	public static int NUM_4x = 0;
	public static int NUM_4y = 0;
	public static int NUM_5x = 0;
	public static int NUM_5y = 0;
	public static int NUM_6x = 0;
	public static int NUM_6y = 0;
	public static int NUM_7x = 0;
	public static int NUM_7y = 0;
	public static int NUM_8x = 0;
	public static int NUM_8y = 0;
	public static int NUM_9x = 0;
	public static int NUM_9y = 0;
	public static int NUM_0x = 0;
	public static int NUM_0y = 0;



	private static void initializeEmulatorKeys() {
		/*Ax = 69;
		Ay = 902;
		Bx = 430;
		By = 1008;
		Cx = 293;
		Cy = 1008;
		Dx = 213;
		Dy = 900;
		Ex = 180;
		Ey = 780;
		Fx = 291;
		Fy = 889;
		Gx = 356;
		Gy = 895;
		Hx = 428;
		Hy = 900;
		Ix = 543;
		Iy = 791;
		Jx = 506;
		Jy = 897;
		Kx = 573;
		Ky = 900;
		Lx = 643;
		Ly = 904;
		Mx = 573;
		My = 1017;
		Nx = 504;
		Ny = 1015;
		Ox = 610;
		Oy = 784;
		Px = 684;
		Py = 797;
		Qx = 39;
		Qy = 789;
		Rx = 254;
		Ry = 797;
		Sx = 152;
		Sy = 906;
		Tx = 326;
		Ty = 789;
		Ux = 465;
		Uy = 789;
		Vx = 352;
		Vy = 1015;
		Wx = 110;
		Wy = 793;
		Xx = 210;
		Xy = 1015;
		Yx = 397;
		Yy = 793;
		Zx = 136;
		Zy = 1017;

		SHIFTx = 52;
		SHIFTy = 1004;
		SYMx = 58;
		SYMy = 1113;
		ONEx = 26;
		ONEy = 795;
		TWOx = 104;
		TWOy = 797;
		THREEx = 182;
		THREEy = 800;
		FOURx = 247;
		FOURy = 806;
		FIVEx = 321;
		FIVEy = 789;
		SIXx = 393;
		SIXy = 793;
		SEVENx = 463;
		SEVENy = 782;
		EIGHTx = 536;
		EIGHTy = 784;
		NINEx = 606;
		NINEy = 800;
		ZEROx = 686;
		ZEROy = 797;

		ATx = 41;
		ATy = 906;
		POUNDx = 102;
		POUNDy = 902;
		DOLLARx = 180;
		DOLLARy = 900;
		PERCENTx = 241;
		PERCENTy = 908;
		AMPx = 326;
		AMPy = 902;
		STARx = 395;
		STARy = 904;
		MINUSx = 465;
		MINUSy = 904;
		PLUSx = 539;
		PLUSy = 904;
		OPEN_PARx = 610;
		OPEN_PARy = 906;
		CLOSE_PARx = 676;
		CLOSE_PARy = 904;
		BANGx = 150;
		BANGy = 1006;
		DOUBLEx = 217;
		DOUBLEy = 1004;
		SINGLEx = 284;
		SINGLEy = 1004;
		COLONx = 365;
		COLONy = 1019;
		SEMIx = 428;
		SEMIy = 1019;
		SLASHx = 500;
		SLASHy = 1010;
		QUESTIONx = 571;
		QUESTIONy = 1017;
		COMMAx = 667;
		COMMAy = 1123;
		PERIODx = 139;
		PERIODy = 1134;
		SPACEx = 571;
		SPACEy = 1126;
		NEXTx = 343;
		NEXTy = 1126;

		NUM_1x = 102;
		NUM_1y = 799;
		NUM_2x = 294;
		NUM_2y = 784;
		NUM_3x = 492;
		NUM_3y = 793;
		NUM_4x = 102;
		NUM_4y = 900;
		NUM_5x = 301;
		NUM_5y = 898;
		NUM_6x = 485;
		NUM_6y = 896;
		NUM_7x = 86;
		NUM_7y = 1021;
		NUM_8x = 291;
		NUM_8y = 1016;
		NUM_9x = 487;
		NUM_9y = 1011;
		NUM_0x = 284;
		NUM_0y = 1115;*/

        // Andrubis key layout
		Ax = 42;
		Ay = 601;
		Bx = 287;
		By = 679;
		Cx = 189;
		Cy = 681;
		Dx = 145;
		Dy = 596;
		Ex = 122;
		Ey = 522;
		Fx = 195;
		Fy = 605;
		Gx = 245;
		Gy = 600;
		Hx = 287;
		Hy = 602;
		Ix = 360;
		Iy = 521;
		Jx = 333;
		Jy = 603;
		Kx = 380;
		Ky = 602;
		Lx = 427;
		Ly = 602;
		Mx = 385;
		My = 681;
		Nx = 333;
		Ny = 681;
		Ox = 408;
		Oy = 527;
		Px = 458;
		Py = 524;
		Qx = 21;
		Qy = 527;
		Rx = 172;
		Ry = 522;
		Sx = 98;
		Sy = 597;
		Tx = 219;
		Ty = 522;
		Ux = 313;
		Uy = 527;
		Vx = 240;
		Vy = 677;
		Wx = 75;
		Wy = 521;
		Xx = 149;
		Xy = 679;
		Yx = 264;
		Yy = 524;
		Zx = 96;
		Zy = 679;
		SHIFTx = 39;
		SHIFTy = 682;
		SYMx = 34;
		SYMy = 755;
		ONEx = 24;
		ONEy = 527;
		TWOx = 65;
		TWOy = 526;
		THREEx = 117;
		THREEy = 525;
		FOURx = 166;
		FOURy = 528;
		FIVEx = 214;
		FIVEy = 527;
		SIXx = 266;
		SIXy = 525;
		SEVENx = 316;
		SEVENy = 526;
		EIGHTx = 363;
		EIGHTy = 527;
		NINEx = 406;
		NINEy = 525;
		ZEROx = 451;
		ZEROy = 528;
		ATx = 19;
		ATy = 603;
		POUNDx = 68;
		POUNDy = 604;
		DOLLARx = 117;
		DOLLARy = 602;
		PERCENTx = 162;
		PERCENTy = 609;
		AMPx = 216;
		AMPy = 605;
		STARx = 263;
		STARy = 602;
		MINUSx = 316;
		MINUSy = 603;
		PLUSx = 354;
		PLUSy = 603;
		OPEN_PARx = 411;
		OPEN_PARy = 605;
		CLOSE_PARx = 460;
		CLOSE_PARy = 601;
		BANGx = 101;
		BANGy = 674;
		DOUBLEx = 139;
		DOUBLEy = 673;
		SINGLEx = 183;
		SINGLEy = 677;
		COLONx = 242;
		COLONy = 683;
		SEMIx = 285;
		SEMIy = 677;
		SLASHx = 340;
		SLASHy = 675;
		QUESTIONx = 389;
		QUESTIONy = 681;
		BACKSPACEx = 449;
		BACKSPACEy = 674;
		COMMAx = 140;
		COMMAy = 763;
		PERIODx = 337;
		PERIODy = 756;
		SPACEx = 237;
		SPACEy = 764;
		NEXTx = 420;
		NEXTy = 755;

		NUM_1x = 67;
		NUM_1y = 519;
		NUM_2x = 200;
		NUM_2y = 521;
		NUM_3x = 332;
		NUM_3y = 519;
		NUM_4x = 60;
		NUM_4y = 600;
		NUM_5x = 201;
		NUM_5y = 598;
		NUM_6x = 325;
		NUM_6y = 601;
		NUM_7x = 55;
		NUM_7y = 675;
		NUM_8x = 200;
		NUM_8y = 675;
		NUM_9x = 329;
		NUM_9y = 677;
		NUM_0x = 187;
		NUM_0y = 757;

	}

	private static void initializeDeviceKeys() {
		Ax = 61;
		Ay = 1005;
		Bx = 437;
		By = 1125;
		Cx = 284;
		Cy = 1125;
		Dx = 214;
		Dy = 1005;
		Ex = 178;
		Ey = 900;
		Fx = 287;
		Fy = 1005;
		Gx = 370;
		Gy = 1005;
		Hx = 439;
		Hy = 1005;
		Ix = 543;
		Iy = 900;
		Jx = 502;
		Jy = 1005;
		Kx = 583;
		Ky = 1005;
		Lx = 655;
		Ly = 1005;
		Mx = 573;
		My = 1125;
		Nx = 488;
		Ny = 1125;
		Ox = 610;
		Oy = 900;
		Px = 680;
		Py = 900;
		Qx = 31;
		Qy = 900;
		Rx = 262;
		Ry = 900;
		Sx = 149;
		Sy = 1005;
		Tx = 328;
		Ty = 900;
		Ux = 465;
		Uy = 900;
		Vx = 373;
		Vy = 1125;
		Wx = 121;
		Wy = 900;
		Xx = 214;
		Xy = 1125;
		Yx = 396;
		Yy = 900;
		Zx = 150;
		Zy = 1125;

		ONEx = 53;
		ONEy = 900;
		TWOx = 124;
		TWOy = 900;
		THREEx = 201;
		THREEy = 900;
		FOURx = 272;
		FOURy = 900;
		FIVEx = 341;
		FIVEy = 900;
		SIXx = 416;
		SIXy = 900;
		SEVENx = 482;
		SEVENy = 900;
		EIGHTx = 562;
		EIGHTy = 900;
		NINEx = 629;
		NINEy = 900;
		ZEROx = 684;
		ZEROy = 900;

		SHIFTx = 53;
		SHIFTy = 1116;
		SYMx = 61;
		SYMy = 1234;
		ATx = 116;
		ATy = 1005;
		PERIODx = 576;
		PERIODy = 1231;
		SPACEx = 363;
		SPACEy = 1220;
		NEXTx = 690;
		NEXTy = 1210;
	
		NUM_1x = 106;
		NUM_1y = 881;
		NUM_2x = 270;
		NUM_2y = 896;
		NUM_3x = 459;
		NUM_3y = 895;
		NUM_4x = 114;
		NUM_4y = 999;
		NUM_5x = 285;
		NUM_5y = 997;
		NUM_6x = 470;
		NUM_6y = 1008;
		NUM_7x = 110;
		NUM_7y = 1126;
		NUM_8x = 286;
		NUM_8y = 1119;
		NUM_9x = 461;
		NUM_9y = 1115;
		NUM_0x = 259;
		NUM_0y = 1234;
	}	
	
	/**
	 * Name: genKeyStroke
	 * Description: This method takes a string and parses out characters to be
	 * 	"pressed" as buttons.  It calls a methods that return the corresponding
	 * 	event code needed to trigger a touch event at the location of each virtual
	 * 	key.
	 * @param input (String)
	 * @return String
	 */
	public static String genKeyStrokes(String input, boolean isNumPad, boolean isEmulator) {
		String sequence = "";//event2\n";
		char[] keys = input.toCharArray();
		for(int i = 0; i < keys.length; i++) {
			if(isNumPad)
				sequence += getNumpadPress(keys[i], isEmulator);
			else
				sequence += getPress(keys[i], isEmulator);
		}
		return sequence;
	}
	
	
	/**
	 * Name: getPress
	 * Description: takes a character as an argument and returns the event code 
	 * 	corresponding to a button press on the virtual keyboard for that char.
	 * @param c
	 * @return
	 */
	public static String getPress(char c, boolean isEmulator) {
		if(isEmulator)
			initializeEmulatorKeys();
		else
			initializeDeviceKeys();
		String press = "";
		if(!Character.isLowerCase(c) && Character.isLetter(c)) {
			press += genericPress(SHIFTx, SHIFTy, PAUSE, isEmulator);
			c = Character.toLowerCase(c);
		}
		if(Character.isDigit(c)) {
			//press += " 3 57 123\n" +
			press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
		}
		if(!Character.isLetterOrDigit(c)) {
			if(!Character.isSpaceChar(c))
				press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
		}
		switch(c) {
			case 'a':
				press += genericPress(Ax,Ay, PAUSE, isEmulator);
				break;
			case 'b':
				press += genericPress(Bx, By, PAUSE, isEmulator);
				break;
			case 'c':
				press += genericPress(Cx, Cy, PAUSE, isEmulator);
				break;
			case 'd':
				press += genericPress(Dx, Dy, PAUSE, isEmulator);
				break;
			case 'e':
				press += genericPress(Ex, Ey, PAUSE, isEmulator);
				break;
			case 'f':
				press += genericPress(Fx, Fy, PAUSE, isEmulator);
				break;
			case 'g':
				press += genericPress(Gx, Gy, PAUSE, isEmulator);
				break;
			case 'h':
				press += genericPress(Hx, Hy, PAUSE, isEmulator);
				break;
			case 'i':
				press += genericPress(Ix, Iy, PAUSE, isEmulator);
				break;
			case 'j':
				press += genericPress(Jx, Jy, PAUSE, isEmulator);
				break;
			case 'k':
				press += genericPress(Kx, Ky, PAUSE, isEmulator);
				break;
			case 'l':
				press += genericPress(Lx, Ly, PAUSE, isEmulator);
				break;
			case 'm':
				press += genericPress(Mx, My, PAUSE, isEmulator);
				break;
			case 'n':
				press += genericPress(Nx, Ny, PAUSE, isEmulator);
				break;
			case 'o':
				press += genericPress(Ox, Oy, PAUSE, isEmulator);
				break;
			case 'p':
				press += genericPress(Px, Py, PAUSE, isEmulator);
				break;
			case 'q':
				press += genericPress(Qx, Qy, PAUSE, isEmulator);
				break;
			case 'r':
				press += genericPress(Rx, Ry, PAUSE, isEmulator);
				break;
			case 's':
				press += genericPress(Sx, Sy, PAUSE, isEmulator);
				break;
			case 't':
				press += genericPress(Tx, Ty, PAUSE, isEmulator);
				break;
			case 'u':
				press += genericPress(Ux, Uy, PAUSE, isEmulator);
				break;
			case 'v':
				press += genericPress(Vx, Vy, PAUSE, isEmulator);
				break;
			case 'w':
				press += genericPress(Wx, Wy, PAUSE, isEmulator);
				break;
			case 'x':
				press += genericPress(Xx, Xy, PAUSE, isEmulator);
				break;
			case 'y':
				press += genericPress(Yx, Yy, PAUSE, isEmulator);
				break;
			case 'z':
				press += genericPress(Zx, Zy, PAUSE, isEmulator);
				break;
			case '1':
				press += genericPress(ONEx, ONEy, PAUSE, isEmulator);
				press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
				break;
			case '2':
				press += genericPress(TWOx, TWOy, PAUSE, isEmulator);
				press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
				break;
			case '3':
				press += genericPress(THREEx, THREEy, PAUSE, isEmulator);
				press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
				break;
			case '4':
				press += genericPress(FOURx, FOURy, PAUSE, isEmulator);
				press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
				break;
			case '5':
				press += genericPress(FIVEx, FIVEy, PAUSE, isEmulator);
				press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
				break;
			case '6':
				press += genericPress(SIXx, SIXy, PAUSE, isEmulator);
				press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
				break;
			case '7':
				press += genericPress(SEVENx, SEVENy, PAUSE, isEmulator);
				press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
				break;
			case '8':
				press += genericPress(EIGHTx, EIGHTy, PAUSE, isEmulator);
				press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
				break;
			case '9':
				press += genericPress(NINEx, NINEy, PAUSE, isEmulator);
				press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
				break;
			case '0':
				press += genericPress(ZEROx, ZEROy, PAUSE, isEmulator);
				press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
				break;
			case '.':
				press += genericPress(PERIODx, PERIODy, PAUSE, isEmulator);
				press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
				break;
			case '@':
				press += genericPress(ATx, ATy, PAUSE, isEmulator);
				press += genericPress(SYMx, SYMy, PAUSE, isEmulator);
				break;
			case ' ':
				press += genericPress(SPACEx, SPACEy, PAUSE, isEmulator);
				break;
			default:
				press = "";
		}
		return press;
	}
	
	public static String getNumpadPress(char c, boolean isEmulator) {
		if(isEmulator)
			initializeEmulatorKeys();
		else
			initializeDeviceKeys();
		String press = "";
		switch(c) {
			case '1':
				press += genericPress(NUM_1x, NUM_1y, PAUSE, isEmulator);
				break;
			case '2':
				press += genericPress(NUM_2x, NUM_2y, PAUSE, isEmulator);
				break;
			case '3':
				press += genericPress(NUM_3x, NUM_3y, PAUSE, isEmulator);
				break;
			case '4':
				press += genericPress(NUM_4x, NUM_4y, PAUSE, isEmulator);
				break;
			case '5':
				press += genericPress(NUM_5x, NUM_5y, PAUSE, isEmulator);
				break;
			case '6':
				press += genericPress(NUM_6x, NUM_6y, PAUSE, isEmulator);
				break;
			case '7':
				press += genericPress(NUM_7x, NUM_7y, PAUSE, isEmulator);
				break;
			case '8':
				press += genericPress(NUM_8x, NUM_8y, PAUSE, isEmulator);
				break;
			case '9':
				press += genericPress(NUM_9x, NUM_9y, PAUSE, isEmulator);
				break;
			case '0':
				press += genericPress(NUM_0x, NUM_0y, PAUSE, isEmulator);
				break;
			default:
				press = "";
		}
		return press;
	}
	
	public static String genericPress(int x, int y, int offset, boolean isEmulator) {
		String press = "";
		if(!isEmulator) {
			press = "event2\n" + offset;
			press += " 3 57 " + id + "\n" +
					"40 3 50 8\n" +
					"15 3 53 " + x  + "\n" +
					"15 3 54 " + y  + "\n" +
					"15 3 48 13\n" +
					"15 3 58 50\n" +
					"250 0 0 0\n" +
					"10000 3 57 4294967295\n" + 
					"8 0 0 0\n";
			id++;
		}
		else {
			press = "event0\n" + offset;
			press += " 3 0 " + x + "\n" +
					"115 3 1 " + y  + "\n" +
					"78 1 330 1\n" +
					"60 0 0 0\n" +
					"67243 1 330 0\n" +
					"130 0 0 0\n";
		}
		
		return press;
	}
	
	public static String pressBackButton(boolean isEmulator) {
		String output = "";
		if(!isEmulator) {
			output = "event9\n1000000 1 158 1\n" +
			"31 0 0 0\n" +
			"79877 1 158 0\n" +
			"14 0 0 0\n";
		}
		else {
			output = "event0\n1000000 1 158 1\n" + 
				"65000 1 158 0\n";
		}
		return output;
	}
	
	public static String pressMenuButton(boolean isEmulator) {
		if(!isEmulator) {
			return "event9\n2000000 1 139 1\n" +
			"31 0 0 0\n" +
			"72919 1 139 0\n" +
			"18 0 0 0\n";
		}
		else {
			return "event0\n1000000 1 229 1\n" + 
					"65000 1 229 0\n";
		}
	}
	
	public static String getSwipe(int startx, int endx, int starty, int endy, int time, boolean isEmulator) {
		String swipe = "";
		if(!isEmulator) {
			swipe = "event2\n1000000 3 57 " + id + "\n" +
				"30 3 50 10\n" +
				"15 3 53 " + startx + "\n" +
				"15 3 54 " + starty + "\n" +
				"15 3 48 15\n" +
				"15 3 49 10\n" +
				"15 3 60 12\n" +
				"200 0 0 0\n";
		}
		else {
			swipe = "event0\n1000000 3 0 " + startx + "\n" +
					"115 3 1 " + starty + "\n" +
					"78 0 0 0\n" +
					"78 1 330 1\n" +
					"60 0 0 0\n";
		}
		
		// The next session involves splitting the change in x and y values into deltas
		// Essentially, I need to calculate the equation of the line formed by the x and y deltas and pull
		//  ~16 pixels offsets from the line.  Speed will be the time offset from start to end. Each increment
		//  will be time-stamped at 0 + increment_number*speed/number_of_increments, cast to an int.
		int deltax = endx - startx;
		int deltay = endy - starty;  // was wrapped in Math.abs() call
		int deltat, numUpdates, xinc, yinc;
		if(Math.abs(deltax) > Math.abs(deltay)) {
			numUpdates = Math.abs(deltax) / 16;
			xinc = 16;
			deltat = time / numUpdates;
			yinc = deltay / numUpdates;
		}
		else if(Math.abs(deltax) < Math.abs(deltay)) {
			numUpdates = Math.abs(deltay) / 16;
			yinc = 16;
			deltat = time / numUpdates;
			xinc = deltax / numUpdates;
		}
		else {
			numUpdates = Math.abs(deltax) / 16;
			xinc = 16;
			yinc = 16;
			deltat = time / numUpdates;
		}
		//Log.i("CuriousDroid", "PressGenerator-->getSwype(): numUpdates = " +numUpdates + ", deltat = " + deltat + 
			//	", xinc = " + xinc + ", yinc = " + yinc);
		for(int i = 1; i < numUpdates; i++) {
			if(!isEmulator) {
				swipe += deltat + " 3 50 10\n" +
					"15 3 53 " + (startx+i*xinc) + "\n" +
					"15 3 54 " + (starty+i*yinc) + "\n" +
					"15 3 48 15\n" +
					"15 3 49 10\n" +
					"15 3 60 12\n" +
					"200 0 0 0\n";
			}
			else {
				swipe += deltat + " 3 0 " + (startx+i*xinc) + "\n" +
					"115 3 1 " + (starty+i*yinc)  + "\n" +
					"60 0 0 0\n";
			}
		}
		if(!isEmulator) {
			swipe += "10000 3 57 4294967295\n" + 
				"8 0 0 0\n";
			id++;
		}
		else {
			swipe += "10000 1 330 0\n" +
				"75 0 0 0\n";
		}
		return swipe;
	}
	
	public static String randomEvents(int xmax, int ymax, int numEvents, boolean isEmulator) {
		String output = "";
		Random rng = new Random();
		for(int i = 0; i < numEvents; i++) {	
			int type = rng.nextInt(2);
			switch(type) {
				case 0:
					int x = rng.nextInt(xmax);
					int y = rng.nextInt(ymax);
					int t = rng.nextInt(1000000);
					output += genericPress(x, y, t, isEmulator);
					break;
				case 1:
					int xstart = rng.nextInt(xmax);
					int xend = rng.nextInt(xmax);
					int ystart = rng.nextInt(ymax);
					int yend = rng.nextInt(ymax);
					int time = rng.nextInt(100000);
					output += getSwipe(xstart, xend, ystart, yend, time, isEmulator);
					break;
			}
		}
		return output;
	}
}
