///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    // TODO:
    int size;
    int tap;
    int[] register;

    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        // TODO:
        this.size = size;
        this.tap = tap;
    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description:
     */
    @Override
    public void setSeed(int[] seed) {
        // TODO:
        register = seed;
    }

    /**
     * shift
     * @return
     * Description:
     */
    @Override
    public int shift() {
        // TODO:
        int[] newRegister = new int[size];

        // perform xor
        int feedbackBit = register[tap] ^ register[size - 1];

        // perform the shift
        for (int i = 1; i < register.length; i++) {
            newRegister[i] = register[i - 1];
        }
        newRegister[0] = feedbackBit;
        register = newRegister;

        // return new least significant bit
        return feedbackBit;
    }

    /**
     * generate
     * @param k
     * @return
     * Description:
     */
    @Override
    public int generate(int k) {
        int binaryNumber = 0;

        for (int i = 1; i <= k; i++) {
            binaryNumber = binaryNumber * 10 + shift();
        }

        String binaryString = Integer.toString(binaryNumber);
        return Integer.parseInt(binaryString, 2);
    }
}
