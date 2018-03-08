package at.saith.twasi.rank.json;

public class JSONFormatter {
	public static String formatJSON(final String json_str) {
		return formatJSON(json_str, 1);
	}
	public static String formatJSON(final String json_str, final int indent_width) {
	    final char[] chars = json_str.toCharArray();
	    final String newline = System.lineSeparator();

	    String ret = "";
	    boolean begin_quotes = false;

	    for (int i = 0, indent = 0; i < chars.length; i++) {
	        char c = chars[i];

	        if (c == '\"') {
	            ret += c;
	            begin_quotes = !begin_quotes;
	            continue;
	        }

	        if (!begin_quotes) {
	            switch (c) {
	            case '{':
	            case '[':
	                ret += c + newline + String.format("%" + (indent += indent_width) + "s", "");
	                continue;
	            case '}':
	            case ']':
	                ret += newline + ((indent -= indent_width) > 0 ? String.format("%" + indent + "s", "") : "") + c;
	                continue;
	            case ':':
	                ret += c + " ";
	                continue;
	            case ',':
	                ret += c + newline + (indent > 0 ? String.format("%" + indent + "s", "") : "");
	                continue;
	            default:
	                if (Character.isWhitespace(c)) continue;
	            }
	        }

	        ret += c + (c == '\\' ? "" + chars[++i] : "");
	    }

	    return ret;
	}
}
