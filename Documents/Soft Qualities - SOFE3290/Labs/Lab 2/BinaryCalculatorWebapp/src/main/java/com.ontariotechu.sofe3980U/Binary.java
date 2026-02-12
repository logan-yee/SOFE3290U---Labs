package com.ontariotechu.sofe3980U;

/**
 * Unsigned integer Binary variable
 *
 */
public class Binary
{
	private String number="0";  // string containing the binary value '0' or '1'
	/**
	* A constructor that generates a binary object.
	*
	* @param number a String of the binary values. It should contain only zeros or ones with any length and order. otherwise, the value of "0" will be stored.   Trailing zeros will be excluded and empty string will be considered as zero.
	*/
	public Binary(String number) {
		if (number == null || number.isEmpty()) {
			this.number = "0"; // Default to "0" for null or empty input
			return;
		}

		// Validate the binary string (only '0' or '1' allowed)
		for (int i = 0; i < number.length(); i++) {
			char ch = number.charAt(i);
			if (ch != '0' && ch != '1') {
				this.number = "0"; // Default to "0" for invalid input
				return;
			}
		}

		// Remove leading zeros
		int beg;
		for (beg = 0; beg < number.length(); beg++) {
			if (number.charAt(beg) != '0') {
				break;
			}
		}

		// If all digits are '0', ensure number is "0"
		this.number = (beg == number.length()) ? "0" : number.substring(beg);

		// Ensure empty strings are replaced with "0"
		if (this.number.isEmpty()) {
			this.number = "0";
		}
	}
	/**
	* Return the binary value of the variable
	*
	* @return the binary value in a string format.
	*/
	public String getValue()
	{
		return this.number;
	}
	/**
	* Adding two binary variables. For more information, visit <a href="https://www.wikihow.com/Add-Binary-Numbers"> Add-Binary-Numbers </a>.
	*
	* @param num1 The first addend object
	* @param num2 The second addend object
	* @return A binary variable with a value of <i>num1+num2</i>.
	*/
	public static Binary add(Binary num1,Binary num2)
	{
		int ind1=num1.number.length()-1;
		int ind2=num2.number.length()-1;
		int carry=0;
		String num3="";
		while(ind1>=0 ||  ind2>=0 || carry!=0)
		{
			int sum=carry;
			if(ind1>=0){
				sum += (num1.number.charAt(ind1)=='1')? 1:0;
				ind1--;
			}
			if(ind2>=0){
				sum += (num2.number.charAt(ind2)=='1')? 1:0;
				ind2--;
			}
			carry=sum/2;
			sum=sum%2;
			num3 =( (sum==0)? "0":"1")+num3;
		}
		return new Binary(num3);
	}

	/**
	 * Bitwise OR between two binary variables.
	 * @param num1 first operand
	 * @param num2 second operand
	 * @return num1 OR num2
	 */
	public static Binary or(Binary num1, Binary num2) {
		if (num1 == null || num2 == null) return new Binary("0");
		int i = num1.number.length() - 1;
		int j = num2.number.length() - 1;
		String result = "";
		while (i >= 0 || j >= 0) {
			char c1 = (i >= 0) ? num1.number.charAt(i) : '0';
			char c2 = (j >= 0) ? num2.number.charAt(j) : '0';
			result = ((c1 == '1' || c2 == '1') ? "1" : "0") + result;
			i--;
			j--;
		}
		return new Binary(result);
	}

	/**
	 * Bitwise AND between two binary variables.
	 * @param num1 first operand
	 * @param num2 second operand
	 * @return num1 AND num2
	 */
	public static Binary and(Binary num1, Binary num2) {
		if (num1 == null || num2 == null) return new Binary("0");
		int i = num1.number.length() - 1;
		int j = num2.number.length() - 1;
		String result = "";
		while (i >= 0 || j >= 0) {
			char c1 = (i >= 0) ? num1.number.charAt(i) : '0';
			char c2 = (j >= 0) ? num2.number.charAt(j) : '0';
			result = ((c1 == '1' && c2 == '1') ? "1" : "0") + result;
			i--;
			j--;
		}
		return new Binary(result);
	}

	/**
	 * Multiply two binary variables using binary long multiplication.
	 * @param num1 multiplicand
	 * @param num2 multiplier
	 * @return num1 * num2
	 */
	public static Binary multiply(Binary num1, Binary num2) {
		if (num1 == null || num2 == null) return new Binary("0");
		if (num1.number.equals("0") || num2.number.equals("0")) return new Binary("0");
		Binary result = new Binary("0");
		for (int k = num2.number.length() - 1; k >= 0; k--) {
			if (num2.number.charAt(k) == '1') {
				int shift = (num2.number.length() - 1) - k;
				StringBuilder sb = new StringBuilder(num1.number);
				for (int s = 0; s < shift; s++) sb.append('0');
				result = Binary.add(result, new Binary(sb.toString()));
			}
		}
		return result;
	}
}
