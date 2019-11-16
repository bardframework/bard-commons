package org.bardframework.commons.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for HTML escaping. Escapes and unescapes
 * based on the W3C HTML 4.01 recommendation, handling
 * character entity references.
 *
 * <p>Reference:
 * <a href="http://www.w3.org/TR/html4/charset.html">http://www.w3.org/TR/html4/charset.html</a>
 *
 * <p>For a comprehensive set of String escaping utilities,
 * consider Apache Commons Lang and its StringEscapeUtils class.
 * We are not using that class here to avoid a runtime dependency
 * on Commons Lang just for HTML escaping. Furthermore, Spring's
 * HTML escaping is more flexible and 100% HTML 4.0 compliant.
 */
public abstract class HtmlUtils {
    /**
     * Shared instance of pre-parsed HTML character entity references.
     */
    private static final HtmlCharacterEntityReferences characterEntityReferences = new HtmlCharacterEntityReferences();

    private HtmlUtils() {
    }

    /**
     * Turn special characters into HTML character references.
     * Handles complete character set defined in HTML 4.01 recommendation.
     * <p>Escapes all special characters to their corresponding
     * entity reference (e.g. {@code &lt;}) at least as required by the
     * specified encoding. In other words, if a special character does
     * not have to be escaped for the given encoding, it may not be.
     * <p>Reference:
     * <a href="http://www.w3.org/TR/html4/sgml/entities.html">
     * http://www.w3.org/TR/html4/sgml/entities.html
     * </a>
     *
     * @param input    the (unescaped) input string
     * @param encoding the name of a supported {@link java.nio.charset.Charset charset}
     * @return the escaped string
     */
    public static String htmlEscape(String input, String encoding) {
        StringBuilder escaped = new StringBuilder(input.length() * 2);
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            String reference = characterEntityReferences.convertToReference(character, encoding);
            if (reference != null) {
                escaped.append(reference);
            } else {
                escaped.append(character);
            }
        }
        return escaped.toString();
    }

    /**
     * Turn HTML character references into their plain text UNICODE equivalent.
     * <p>Handles complete character set defined in HTML 4.01 recommendation
     * and all reference types (decimal, hex, and entity).
     * <p>Correctly converts the following formats:
     * <blockquote>
     * &amp;#<i>Entity</i>; - <i>(Example: &amp;amp;) case sensitive</i>
     * &amp;#<i>Decimal</i>; - <i>(Example: &amp;#68;)</i><br>
     * &amp;#x<i>Hex</i>; - <i>(Example: &amp;#xE5;) case insensitive</i><br>
     * </blockquote>
     * Gracefully handles malformed character references by copying original
     * characters as is when encountered.<p>
     * <p>Reference:
     * <a href="http://www.w3.org/TR/html4/sgml/entities.html">
     * http://www.w3.org/TR/html4/sgml/entities.html
     * </a>
     *
     * @param input the (escaped) input string
     * @return the unescaped string
     */
    public static String htmlUnescape(String input) {
        return new HtmlCharacterEntityDecoder(characterEntityReferences, input).decode();
    }

    /**
     * Helper for decoding HTML Strings by replacing character
     * entity references with the referred character.
     */
    private static class HtmlCharacterEntityDecoder {

        private static final int MAX_REFERENCE_SIZE = 10;

        private final HtmlCharacterEntityReferences characterEntityReferences;

        private final String originalMessage;

        private final StringBuilder decodedMessage;

        private int currentPosition = 0;

        private int nextPotentialReferencePosition = -1;

        private int nextSemicolonPosition = -2;


        HtmlCharacterEntityDecoder(HtmlCharacterEntityReferences characterEntityReferences, String original) {
            this.characterEntityReferences = characterEntityReferences;
            this.originalMessage = original;
            this.decodedMessage = new StringBuilder(original.length());
        }

        String decode() {
            while (this.currentPosition < this.originalMessage.length()) {
                findNextPotentialReference(this.currentPosition);
                copyCharactersTillPotentialReference();
                processPossibleReference();
            }
            return this.decodedMessage.toString();
        }

        private void findNextPotentialReference(int startPosition) {
            this.nextPotentialReferencePosition = Math.max(startPosition, this.nextSemicolonPosition - MAX_REFERENCE_SIZE);

            do {
                this.nextPotentialReferencePosition =
                        this.originalMessage.indexOf('&', this.nextPotentialReferencePosition);

                if (this.nextSemicolonPosition != -1 &&
                        this.nextSemicolonPosition < this.nextPotentialReferencePosition) {
                    this.nextSemicolonPosition = this.originalMessage.indexOf(';', this.nextPotentialReferencePosition + 1);
                }

                boolean isPotentialReference = (this.nextPotentialReferencePosition != -1 &&
                        this.nextSemicolonPosition != -1 &&
                        this.nextPotentialReferencePosition - this.nextSemicolonPosition < MAX_REFERENCE_SIZE);

                if (isPotentialReference || this.nextPotentialReferencePosition == -1) {
                    break;
                }

                if (this.nextSemicolonPosition != -1) {
                    this.nextPotentialReferencePosition = this.nextPotentialReferencePosition + 1;

                } else {
                    this.nextPotentialReferencePosition = -1;
                }

            }
            while (this.nextPotentialReferencePosition != -1);
        }

        private void copyCharactersTillPotentialReference() {
            if (this.nextPotentialReferencePosition != this.currentPosition) {
                int skipUntilIndex = (this.nextPotentialReferencePosition != -1 ? this.nextPotentialReferencePosition : this.originalMessage.length());
                if (skipUntilIndex - this.currentPosition > 3) {
                    this.decodedMessage.append(this.originalMessage, this.currentPosition, skipUntilIndex);
                    this.currentPosition = skipUntilIndex;
                } else {
                    while (this.currentPosition < skipUntilIndex) {
                        this.decodedMessage.append(this.originalMessage.charAt(this.currentPosition++));
                    }
                }
            }
        }

        private void processPossibleReference() {
            if (this.nextPotentialReferencePosition != -1) {
                boolean isNumberedReference = (this.originalMessage.charAt(this.currentPosition + 1) == '#');
                boolean wasProcessable = isNumberedReference ? processNumberedReference() : processNamedReference();
                if (wasProcessable) {
                    this.currentPosition = this.nextSemicolonPosition + 1;
                } else {
                    char currentChar = this.originalMessage.charAt(this.currentPosition);
                    this.decodedMessage.append(currentChar);
                    this.currentPosition++;
                }
            }
        }

        private boolean processNumberedReference() {
            char referenceChar = this.originalMessage.charAt(this.nextPotentialReferencePosition + 2);
            boolean isHexNumberedReference = (referenceChar == 'x' || referenceChar == 'X');
            try {
                int value = (!isHexNumberedReference ? Integer.parseInt(getReferenceSubstring(2)) : Integer.parseInt(getReferenceSubstring(3), 16));
                this.decodedMessage.append((char) value);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }

        private boolean processNamedReference() {
            String referenceName = getReferenceSubstring(1);
            char mappedCharacter = this.characterEntityReferences.convertToCharacter(referenceName);
            if (mappedCharacter != HtmlCharacterEntityReferences.CHAR_NULL) {
                this.decodedMessage.append(mappedCharacter);
                return true;
            }
            return false;
        }

        private String getReferenceSubstring(int referenceOffset) {
            return this.originalMessage.substring(this.nextPotentialReferencePosition + referenceOffset, this.nextSemicolonPosition);
        }
    }

    /**
     * Represents a set of character entity references defined by the
     * HTML 4.0 standard.
     *
     * <p>A complete description of the HTML 4.0 character set can be found
     * at http://www.w3.org/TR/html4/charset.html.
     */
    private static class HtmlCharacterEntityReferences {

        static final char CHAR_NULL = (char) -1;
        private static final char REFERENCE_START = '&';
        private static final char REFERENCE_END = ';';
        private final String[] characterToEntityReferenceMap = new String[3000];

        private final Map<String, Character> entityReferenceToCharacterMap = new HashMap<>(512);

        /**
         * Returns a new set of character entity references reflecting the HTML 4.0 character set.
         */
        HtmlCharacterEntityReferences() {
            String chars = "160=nbsp\n" +
                    "161=iexcl\n" +
                    "162=cent\n" +
                    "163=pound\n" +
                    "164=curren\n" +
                    "165=yen\n" +
                    "166=brvbar\n" +
                    "167=sect\n" +
                    "168=uml\n" +
                    "169=copy\n" +
                    "170=ordf\n" +
                    "171=laquo\n" +
                    "172=not\n" +
                    "173=shy\n" +
                    "174=reg\n" +
                    "175=macr\n" +
                    "176=deg\n" +
                    "177=plusmn\n" +
                    "178=sup2\n" +
                    "179=sup3\n" +
                    "180=acute\n" +
                    "181=micro\n" +
                    "182=para\n" +
                    "183=middot\n" +
                    "184=cedil\n" +
                    "185=sup1\n" +
                    "186=ordm\n" +
                    "187=raquo\n" +
                    "188=frac14\n" +
                    "189=frac12\n" +
                    "190=frac34\n" +
                    "191=iquest\n" +
                    "192=Agrave\n" +
                    "193=Aacute\n" +
                    "194=Acirc\n" +
                    "195=Atilde\n" +
                    "196=Auml\n" +
                    "197=Aring\n" +
                    "198=AElig\n" +
                    "199=Ccedil\n" +
                    "200=Egrave\n" +
                    "201=Eacute\n" +
                    "202=Ecirc\n" +
                    "203=Euml\n" +
                    "204=Igrave\n" +
                    "205=Iacute\n" +
                    "206=Icirc\n" +
                    "207=Iuml\n" +
                    "208=ETH\n" +
                    "209=Ntilde\n" +
                    "210=Ograve\n" +
                    "211=Oacute\n" +
                    "212=Ocirc\n" +
                    "213=Otilde\n" +
                    "214=Ouml\n" +
                    "215=times\n" +
                    "216=Oslash\n" +
                    "217=Ugrave\n" +
                    "218=Uacute\n" +
                    "219=Ucirc\n" +
                    "220=Uuml\n" +
                    "221=Yacute\n" +
                    "222=THORN\n" +
                    "223=szlig\n" +
                    "224=agrave\n" +
                    "225=aacute\n" +
                    "226=acirc\n" +
                    "227=atilde\n" +
                    "228=auml\n" +
                    "229=aring\n" +
                    "230=aelig\n" +
                    "231=ccedil\n" +
                    "232=egrave\n" +
                    "233=eacute\n" +
                    "234=ecirc\n" +
                    "235=euml\n" +
                    "236=igrave\n" +
                    "237=iacute\n" +
                    "238=icirc\n" +
                    "239=iuml\n" +
                    "240=eth\n" +
                    "241=ntilde\n" +
                    "242=ograve\n" +
                    "243=oacute\n" +
                    "244=ocirc\n" +
                    "245=otilde\n" +
                    "246=ouml\n" +
                    "247=divide\n" +
                    "248=oslash\n" +
                    "249=ugrave\n" +
                    "250=uacute\n" +
                    "251=ucirc\n" +
                    "252=uuml\n" +
                    "253=yacute\n" +
                    "254=thorn\n" +
                    "255=yuml\n" +
                    "\n" +
                    "402=fnof\n" +
                    "913=Alpha\n" +
                    "914=Beta\n" +
                    "915=Gamma\n" +
                    "916=Delta\n" +
                    "917=Epsilon\n" +
                    "918=Zeta\n" +
                    "919=Eta\n" +
                    "920=Theta\n" +
                    "921=Iota\n" +
                    "922=Kappa\n" +
                    "923=Lambda\n" +
                    "924=Mu\n" +
                    "925=Nu\n" +
                    "926=Xi\n" +
                    "927=Omicron\n" +
                    "928=Pi\n" +
                    "929=Rho\n" +
                    "931=Sigma\n" +
                    "932=Tau\n" +
                    "933=Upsilon\n" +
                    "934=Phi\n" +
                    "935=Chi\n" +
                    "936=Psi\n" +
                    "937=Omega\n" +
                    "945=alpha\n" +
                    "946=beta\n" +
                    "947=gamma\n" +
                    "948=delta\n" +
                    "949=epsilon\n" +
                    "950=zeta\n" +
                    "951=eta\n" +
                    "952=theta\n" +
                    "953=iota\n" +
                    "954=kappa\n" +
                    "955=lambda\n" +
                    "956=mu\n" +
                    "957=nu\n" +
                    "958=xi\n" +
                    "959=omicron\n" +
                    "960=pi\n" +
                    "961=rho\n" +
                    "962=sigmaf\n" +
                    "963=sigma\n" +
                    "964=tau\n" +
                    "965=upsilon\n" +
                    "966=phi\n" +
                    "967=chi\n" +
                    "968=psi\n" +
                    "969=omega\n" +
                    "977=thetasym\n" +
                    "978=upsih\n" +
                    "982=piv\n" +
                    "8226=bull\n" +
                    "8230=hellip\n" +
                    "8242=prime\n" +
                    "8243=Prime\n" +
                    "8254=oline\n" +
                    "8260=frasl\n" +
                    "8472=weierp\n" +
                    "8465=image\n" +
                    "8476=real\n" +
                    "8482=trade\n" +
                    "8501=alefsym\n" +
                    "8592=larr\n" +
                    "8593=uarr\n" +
                    "8594=rarr\n" +
                    "8595=darr\n" +
                    "8596=harr\n" +
                    "8629=crarr\n" +
                    "8656=lArr\n" +
                    "8657=uArr\n" +
                    "8658=rArr\n" +
                    "8659=dArr\n" +
                    "8660=hArr\n" +
                    "8704=forall\n" +
                    "8706=part\n" +
                    "8707=exist\n" +
                    "8709=empty\n" +
                    "8711=nabla\n" +
                    "8712=isin\n" +
                    "8713=notin\n" +
                    "8715=ni\n" +
                    "8719=prod\n" +
                    "8721=sum\n" +
                    "8722=minus\n" +
                    "8727=lowast\n" +
                    "8730=radic\n" +
                    "8733=prop\n" +
                    "8734=infin\n" +
                    "8736=ang\n" +
                    "8743=and\n" +
                    "8744=or\n" +
                    "8745=cap\n" +
                    "8746=cup\n" +
                    "8747=int\n" +
                    "8756=there4\n" +
                    "8764=sim\n" +
                    "8773=cong\n" +
                    "8776=asymp\n" +
                    "8800=ne\n" +
                    "8801=equiv\n" +
                    "8804=le\n" +
                    "8805=ge\n" +
                    "8834=sub\n" +
                    "8835=sup\n" +
                    "8836=nsub\n" +
                    "8838=sube\n" +
                    "8839=supe\n" +
                    "8853=oplus\n" +
                    "8855=otimes\n" +
                    "8869=perp\n" +
                    "8901=sdot\n" +
                    "8968=lceil\n" +
                    "8969=rceil\n" +
                    "8970=lfloor\n" +
                    "8971=rfloor\n" +
                    "9001=lang\n" +
                    "9002=rang\n" +
                    "9674=loz\n" +
                    "9824=spades\n" +
                    "9827=clubs\n" +
                    "9829=hearts\n" +
                    "9830=diams\n" +
                    "\n" +
                    "34=quot\n" +
                    "38=amp\n" +
                    "39=#39\n" +
                    "60=lt\n" +
                    "62=gt\n" +
                    "338=OElig\n" +
                    "339=oelig\n" +
                    "352=Scaron\n" +
                    "353=scaron\n" +
                    "376=Yuml\n" +
                    "710=circ\n" +
                    "732=tilde\n" +
                    "8194=ensp\n" +
                    "8195=emsp\n" +
                    "8201=thinsp\n" +
                    "8204=zwnj\n" +
                    "8205=zwj\n" +
                    "8206=lrm\n" +
                    "8207=rlm\n" +
                    "8211=ndash\n" +
                    "8212=mdash\n" +
                    "8216=lsquo\n" +
                    "8217=rsquo\n" +
                    "8218=sbquo\n" +
                    "8220=ldquo\n" +
                    "8221=rdquo\n" +
                    "8222=bdquo\n" +
                    "8224=dagger\n" +
                    "8225=Dagger\n" +
                    "8240=permil\n" +
                    "8249=lsaquo\n" +
                    "8250=rsaquo\n" +
                    "8364=euro";
            // Parse reference definition properties
            String[] keys = chars.split("\n");
            for (String keyValueString : keys) {
                if (keyValueString.trim().isEmpty()) {
                    continue;
                }
                String[] keyValue = keyValueString.split("=");
                int referredChar = Integer.parseInt(keyValue[0]);
                int index = (referredChar < 1000 ? referredChar : referredChar - 7000);
                this.characterToEntityReferenceMap[index] = REFERENCE_START + keyValue[1] + REFERENCE_END;
                this.entityReferenceToCharacterMap.put(keyValue[1], (char) referredChar);
            }
        }


        /**
         * Return the reference mapped to the given character, or {@code null} if none found.
         */
        String convertToReference(char character, String encoding) {
            if (encoding.startsWith("UTF-")) {
                switch (character) {
                    case '<':
                        return "&lt;";
                    case '>':
                        return "&gt;";
                    case '"':
                        return "&quot;";
                    case '&':
                        return "&amp;";
                    case '\'':
                        return "&#39;";
                }
            } else if (character < 1000 || (character >= 8000 && character < 10000)) {
                int index = (character < 1000 ? character : character - 7000);
                return this.characterToEntityReferenceMap[index];
            }
            return null;
        }

        /**
         * Return the char mapped to the given entityReference or -1.
         */
        char convertToCharacter(String entityReference) {
            Character referredCharacter = this.entityReferenceToCharacterMap.get(entityReference);
            if (referredCharacter != null) {
                return referredCharacter;
            }
            return CHAR_NULL;
        }
    }
}
