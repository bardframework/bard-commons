package org.bardframework.commons.utils.data;

import lombok.Getter;

import java.util.*;
import java.util.regex.Pattern;

/**
 * <a href="http://en.wikipedia.org/wiki/ISO_3166-1">ISO 3166-1</a> country code.
 * Enum names of this enum themselves are represented by
 * <a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2">ISO 3166-1 alpha-2</a>
 * code (2-letter upper-case alphabets). There are instance methods to get the country name ({@link #getName()}), the
 * <a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-3" >ISO 3166-1 alpha-3</a> code ({@link #getAlpha3()}) and the
 * <a href="http://en.wikipedia.org/wiki/ISO_3166-1_numeric">ISO 3166-1 numeric</a> code ({@link #getNumeric()}).
 * In addition, there are static methods to get a {@code CountryCode} instance that
 * corresponds to a given alpha-2/alpha-3/numeric code ({@link #getByCode(String)}, {@link #getByCode(int)}).
 */
@Getter
public enum CountryCode {

    /**
     * Undefined [UNDEFINED, null, -1, User assigned]
     *
     * <p>
     * This is not an official ISO 3166-1 code.
     * </p>
     */
    UNDEFINED("Undefined", null, -1) {
        @Override
        public Locale toLocale() {
            return LocaleCode.undefined.toLocale();
        }
    },

    AC("Ascension Island", "ASC", -1),

    AD("Andorra", "AND", 20),

    AE("United Arab Emirates", "ARE", 784),

    AF("Afghanistan", "AFG", 4),

    AG("Antigua and Barbuda", "ATG", 28),

    AI("Anguilla", "AIA", 660),

    AL("Albania", "ALB", 8),

    AM("Armenia", "ARM", 51),

    AN("Netherlands Antilles", "ANT", 530),

    AO("Angola", "AGO", 24),

    AQ("Antarctica", "ATA", 10),

    AR("Argentina", "ARG", 32),

    AS("American Samoa", "ASM", 16),

    AT("Austria", "AUT", 40),

    AU("Australia", "AUS", 36),

    AW("Aruba", "ABW", 533),

    /**
     * <a href="http://en.wikipedia.org/wiki/%C3%85land_Islands">&Aring;land Islands</a>
     * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AX">AX</a>, ALA, 248,
     * Officially assigned]
     *
     * <p>
     * The country name was changed from "\u212Bland Islands" (up to 1.14)
     * to "\u00C5land Islands" (since 1.15).
     * </p>
     */
    AX("\u00C5land Islands", "ALA", 248),

    AZ("Azerbaijan", "AZE", 31),

    BA("Bosnia and Herzegovina", "BIH", 70),

    BB("Barbados", "BRB", 52),

    BD("Bangladesh", "BGD", 50),

    BE("Belgium", "BEL", 56),

    BF("Burkina Faso", "BFA", 854),

    BG("Bulgaria", "BGR", 100),

    BH("Bahrain", "BHR", 48),

    BI("Burundi", "BDI", 108),

    BJ("Benin", "BEN", 204),

    BL("Saint Barth\u00E9lemy", "BLM", 652),

    BM("Bermuda", "BMU", 60),

    BN("Brunei Darussalam", "BRN", 96),

    BO("Bolivia, Plurinational State of", "BOL", 68),

    BQ("Bonaire, Sint Eustatius and Saba", "BES", 535),

    BR("Brazil", "BRA", 76),

    BS("Bahamas", "BHS", 44),

    BT("Bhutan", "BTN", 64),

    BU("Burma", "BUR", 104),

    BV("Bouvet Island", "BVT", 74),

    BW("Botswana", "BWA", 72),

    BY("Belarus", "BLR", 112),

    BZ("Belize", "BLZ", 84),

    CA("Canada", "CAN", 124) {
        @Override
        public Locale toLocale() {
            return Locale.CANADA;
        }
    },

    CC("Cocos (Keeling) Islands", "CCK", 166),

    CD("Congo, the Democratic Republic of the", "COD", 180),

    CF("Central African Republic", "CAF", 140),

    CG("Congo", "COG", 178),

    CH("Switzerland", "CHE", 756),

    CI("C\u00F4te d'Ivoire", "CIV", 384),

    CK("Cook Islands", "COK", 184),

    CL("Chile", "CHL", 152),

    CM("Cameroon", "CMR", 120),

    CN("China", "CHN", 156) {
        @Override
        public Locale toLocale() {
            return Locale.CHINA;
        }
    },

    CO("Colombia", "COL", 170),

    CP("Clipperton Island", "CPT", -1),

    CR("Costa Rica", "CRI", 188),

    CS("Serbia and Montenegro", "SCG", 891),

    CU("Cuba", "CUB", 192),

    CV("Cape Verde", "CPV", 132),

    CW("Cura\u00E7ao", "CUW", 531),

    CX("Christmas Island", "CXR", 162),

    CY("Cyprus", "CYP", 196),

    CZ("Czech Republic", "CZE", 203),

    DE("Germany", "DEU", 276) {
        @Override
        public Locale toLocale() {
            return Locale.GERMANY;
        }
    },

    DG("Diego Garcia", "DGA", -1),

    DJ("Djibouti", "DJI", 262),

    DK("Denmark", "DNK", 208),

    DM("Dominica", "DMA", 212),

    DO("Dominican Republic", "DOM", 214),

    DZ("Algeria", "DZA", 12),

    EA("Ceuta, Melilla", null, -1),

    EC("Ecuador", "ECU", 218),

    EE("Estonia", "EST", 233),

    EG("Egypt", "EGY", 818),

    EH("Western Sahara", "ESH", 732),

    ER("Eritrea", "ERI", 232),

    ES("Spain", "ESP", 724),

    ET("Ethiopia", "ETH", 231),

    EU("European Union", null, -1),

    EZ("Eurozone", null, -1),

    FI("Finland", "FIN", 246),

    FJ("Fiji", "FJI", 242),

    FK("Falkland Islands (Malvinas)", "FLK", 238),

    FM("Micronesia, Federated States of", "FSM", 583),

    FO("Faroe Islands", "FRO", 234),

    FR("France", "FRA", 250) {
        @Override
        public Locale toLocale() {
            return Locale.FRANCE;
        }
    },

    FX("France, Metropolitan", "FXX", 249),

    GA("Gabon", "GAB", 266),

    GB("United Kingdom", "GBR", 826) {
        @Override
        public Locale toLocale() {
            return Locale.UK;
        }
    },

    GD("Grenada", "GRD", 308),

    GE("Georgia", "GEO", 268),

    GF("French Guiana", "GUF", 254),

    GG("Guernsey", "GGY", 831),

    GH("Ghana", "GHA", 288),

    GI("Gibraltar", "GIB", 292),

    GL("Greenland", "GRL", 304),

    GM("Gambia", "GMB", 270),

    GN("Guinea", "GIN", 324),

    GP("Guadeloupe", "GLP", 312),

    GQ("Equatorial Guinea", "GNQ", 226),

    GR("Greece", "GRC", 300),

    GS("South Georgia and the South Sandwich Islands", "SGS", 239),

    GT("Guatemala", "GTM", 320),

    GU("Guam", "GUM", 316),

    GW("Guinea-Bissau", "GNB", 624),

    GY("Guyana", "GUY", 328),

    HK("Hong Kong", "HKG", 344),

    HM("Heard Island and McDonald Islands", "HMD", 334),

    HN("Honduras", "HND", 340),

    HR("Croatia", "HRV", 191),

    HT("Haiti", "HTI", 332),

    HU("Hungary", "HUN", 348),

    IC("Canary Islands", null, -1),

    ID("Indonesia", "IDN", 360),

    IE("Ireland", "IRL", 372),

    IL("Israel", "ISR", 376),

    IM("Isle of Man", "IMN", 833),

    IN("India", "IND", 356),

    IO("British Indian Ocean Territory", "IOT", 86),

    IQ("Iraq", "IRQ", 368),

    IR("Iran, Islamic Republic of", "IRN", 364),

    IS("Iceland", "ISL", 352),

    IT("Italy", "ITA", 380) {
        @Override
        public Locale toLocale() {
            return Locale.ITALY;
        }
    },

    JE("Jersey", "JEY", 832),

    JM("Jamaica", "JAM", 388),

    JO("Jordan", "JOR", 400),

    JP("Japan", "JPN", 392) {
        @Override
        public Locale toLocale() {
            return Locale.JAPAN;
        }
    },

    KE("Kenya", "KEN", 404),

    KG("Kyrgyzstan", "KGZ", 417),

    KH("Cambodia", "KHM", 116),

    KI("Kiribati", "KIR", 296),

    KM("Comoros", "COM", 174),

    KN("Saint Kitts and Nevis", "KNA", 659),

    KP("Korea, Democratic People's Republic of", "PRK", 408),

    KR("Korea, Republic of", "KOR", 410) {
        @Override
        public Locale toLocale() {
            return Locale.KOREA;
        }
    },

    KW("Kuwait", "KWT", 414),

    KY("Cayman Islands", "CYM", 136),

    KZ("Kazakhstan", "KAZ", 398),

    LA("Lao People's Democratic Republic", "LAO", 418),

    LB("Lebanon", "LBN", 422),

    LC("Saint Lucia", "LCA", 662),

    LI("Liechtenstein", "LIE", 438),

    LK("Sri Lanka", "LKA", 144),

    LR("Liberia", "LBR", 430),

    LS("Lesotho", "LSO", 426),

    LT("Lithuania", "LTU", 440),

    LU("Luxembourg", "LUX", 442),

    LV("Latvia", "LVA", 428),

    LY("Libya", "LBY", 434),

    MA("Morocco", "MAR", 504),

    MC("Monaco", "MCO", 492),

    MD("Moldova, Republic of", "MDA", 498),

    ME("Montenegro", "MNE", 499),

    MF("Saint Martin (French part)", "MAF", 663),

    MG("Madagascar", "MDG", 450),

    MH("Marshall Islands", "MHL", 584),

    MK("North Macedonia, Republic of", "MKD", 807),

    ML("Mali", "MLI", 466),

    MM("Myanmar", "MMR", 104),

    MN("Mongolia", "MNG", 496),

    MO("Macao", "MAC", 446),

    MP("Northern Mariana Islands", "MNP", 580),

    MQ("Martinique", "MTQ", 474),

    MR("Mauritania", "MRT", 478),

    MS("Montserrat", "MSR", 500),

    MT("Malta", "MLT", 470),

    MU("Mauritius", "MUS", 480),

    MV("Maldives", "MDV", 462),

    MW("Malawi", "MWI", 454),

    MX("Mexico", "MEX", 484),

    MY("Malaysia", "MYS", 458),

    MZ("Mozambique", "MOZ", 508),

    NA("Namibia", "NAM", 516),

    NC("New Caledonia", "NCL", 540),

    NE("Niger", "NER", 562),

    NF("Norfolk Island", "NFK", 574),

    NG("Nigeria", "NGA", 566),

    NI("Nicaragua", "NIC", 558),

    NL("Netherlands", "NLD", 528),

    NO("Norway", "NOR", 578),

    NP("Nepal", "NPL", 524),

    NR("Nauru", "NRU", 520),

    NT("Neutral Zone", "NTZ", 536),

    NU("Niue", "NIU", 570),

    NZ("New Zealand", "NZL", 554),

    OM("Oman", "OMN", 512),

    PA("Panama", "PAN", 591),

    PE("Peru", "PER", 604),

    PF("French Polynesia", "PYF", 258),

    PG("Papua New Guinea", "PNG", 598),

    PH("Philippines", "PHL", 608),

    PK("Pakistan", "PAK", 586),

    PL("Poland", "POL", 616),

    PM("Saint Pierre and Miquelon", "SPM", 666),

    PN("Pitcairn", "PCN", 612),

    PR("Puerto Rico", "PRI", 630),

    PS("Palestine, State of", "PSE", 275),

    PT("Portugal", "PRT", 620),

    PW("Palau", "PLW", 585),

    PY("Paraguay", "PRY", 600),

    QA("Qatar", "QAT", 634),

    RE("R\u00E9union", "REU", 638),

    RO("Romania", "ROU", 642),

    RS("Serbia", "SRB", 688),

    RU("Russian Federation", "RUS", 643),

    RW("Rwanda", "RWA", 646),

    SA("Saudi Arabia", "SAU", 682),

    SB("Solomon Islands", "SLB", 90),

    SC("Seychelles", "SYC", 690),

    SD("Sudan", "SDN", 729),

    SE("Sweden", "SWE", 752),

    SF("Finland", "FIN", 246),

    SG("Singapore", "SGP", 702),

    SH("Saint Helena, Ascension and Tristan da Cunha", "SHN", 654),

    SI("Slovenia", "SVN", 705),

    SJ("Svalbard and Jan Mayen", "SJM", 744),

    SK("Slovakia", "SVK", 703),

    SL("Sierra Leone", "SLE", 694),

    SM("San Marino", "SMR", 674),

    SN("Senegal", "SEN", 686),

    SO("Somalia", "SOM", 706),

    SR("Suriname", "SUR", 740),

    SS("South Sudan", "SSD", 728),

    ST("Sao Tome and Principe", "STP", 678),

    SU("USSR", "SUN", 810),

    SV("El Salvador", "SLV", 222),

    SX("Sint Maarten (Dutch part)", "SXM", 534),

    SY("Syrian Arab Republic", "SYR", 760),

    SZ("Eswatini", "SWZ", 748),

    TA("Tristan da Cunha", "TAA", -1),

    TC("Turks and Caicos Islands", "TCA", 796),

    TD("Chad", "TCD", 148),

    TF("French Southern Territories", "ATF", 260),

    TG("Togo", "TGO", 768),

    TH("Thailand", "THA", 764),

    TJ("Tajikistan", "TJK", 762),

    TK("Tokelau", "TKL", 772),

    TL("Timor-Leste", "TLS", 626),

    TM("Turkmenistan", "TKM", 795),

    TN("Tunisia", "TUN", 788),

    TO("Tonga", "TON", 776),

    TP("East Timor", "TMP", 626),

    TR("Turkey", "TUR", 792),

    TT("Trinidad and Tobago", "TTO", 780),

    TV("Tuvalu", "TUV", 798),

    TW("Taiwan, Province of China", "TWN", 158) {
        @Override
        public Locale toLocale() {
            return Locale.TAIWAN;
        }
    },

    TZ("Tanzania, United Republic of", "TZA", 834),

    UA("Ukraine", "UKR", 804),

    UG("Uganda", "UGA", 800),

    UK("United Kingdom", null, 826) {
        @Override
        public Locale toLocale() {
            return Locale.UK;
        }
    },

    UM("United States Minor Outlying Islands", "UMI", 581),

    US("United States", "USA", 840) {
        @Override
        public Locale toLocale() {
            return Locale.US;
        }
    },

    UY("Uruguay", "URY", 858),

    UZ("Uzbekistan", "UZB", 860),

    VA("Holy See (Vatican City State)", "VAT", 336),

    VC("Saint Vincent and the Grenadines", "VCT", 670),

    VE("Venezuela, Bolivarian Republic of", "VEN", 862),

    VG("Virgin Islands, British", "VGB", 92),

    VI("Virgin Islands, U.S.", "VIR", 850),

    VN("Viet Nam", "VNM", 704),

    VU("Vanuatu", "VUT", 548),

    WF("Wallis and Futuna", "WLF", 876),

    WS("Samoa", "WSM", 882),

    XI("Northern Ireland", "XXI", -1),

    XU("United Kingdom (excluding Northern Ireland)", "XXU", -1),

    XK("Kosovo, Republic of", "XKX", -1),

    YE("Yemen", "YEM", 887),

    YT("Mayotte", "MYT", 175),

    YU("Yugoslavia", "YUG", 890),

    ZA("South Africa", "ZAF", 710),

    ZM("Zambia", "ZMB", 894),

    ZR("Zaire", "ZAR", 180),

    ZW("Zimbabwe", "ZWE", 716),
    ;


    private static final Map<String, CountryCode> alpha3Map = new HashMap<>();
    private static final Map<String, CountryCode> alpha4Map = new HashMap<>();
    private static final Map<Integer, CountryCode> numericMap = new HashMap<>();

    static {
        for (CountryCode cc : values()) {
            if (cc.getAlpha3() != null) {
                alpha3Map.put(cc.getAlpha3(), cc);
            }

            if (cc.getNumeric() != -1) {
                numericMap.put(cc.getNumeric(), cc);
            }
        }

        // FI and SF have the same alpha-3 code "FIN". FI should be used.
        alpha3Map.put("FIN", FI);

        // For backward compatibility.
        alpha4Map.put("ANHH", AN);
        alpha4Map.put("BUMM", BU);
        alpha4Map.put("CSXX", CS);
        alpha4Map.put("NTHH", NT);
        alpha4Map.put("TPTL", TP);
        alpha4Map.put("YUCS", YU);
        alpha4Map.put("ZRCD", ZR);

        // BU and MM have the same numeric code 104. MM should be used.
        numericMap.put(104, MM);

        // CD and ZR have the same numeric code 180. CD should be used.
        numericMap.put(180, CD);

        // FI and SF have the same numeric code 246. FI should be used.
        numericMap.put(246, FI);

        // GB and UK have the same numeric code 826. GB should be used.
        numericMap.put(826, GB);

        // TL and TP have the same numeric code 626. TL should be used.
        numericMap.put(626, TL);

        // County code 280 is also used for DE by the German banking industry.
        numericMap.put(280, DE);
    }

    private final String name;
    private final String alpha3;
    private final int numeric;

    CountryCode(String name, String alpha3, int numeric) {
        this.name = name;
        this.alpha3 = alpha3;
        this.numeric = numeric;
    }

    public static CountryCode getByCode(String code) {
        return getByCode(code, true);
    }

    public static CountryCode getByCodeIgnoreCase(String code) {
        return getByCode(code, false);
    }

    public static CountryCode getByCode(String code, boolean caseSensitive) {
        if (code == null) {
            return null;
        }

        switch (code.length()) {
            case 2:
                code = canonicalize(code, caseSensitive);
                return getByAlpha2Code(code);

            case 3:
                code = canonicalize(code, caseSensitive);
                return getByAlpha3Code(code);

            case 4:
                code = canonicalize(code, caseSensitive);
                return getByAlpha4Code(code);

            case 9:
                code = canonicalize(code, caseSensitive);
                if ("UNDEFINED".equals(code)) {
                    return CountryCode.UNDEFINED;
                }
                // FALLTHROUGH

            default:
                return null;
        }
    }

    /**
     * Get a {@code CountryCode} that corresponds to the country code of
     * the given {@link Locale} instance.
     *
     * @param locale A {@code Locale} instance.
     * @return A {@code CountryCode} instance, or {@code null} if not found.
     * When {@link Locale#getCountry() getCountry()} method of the
     * given {@code Locale} instance returns {@code null} or an
     * empty string, {@link #UNDEFINED CountryCode.UNDEFINED} is
     * returned.
     * @see Locale#getCountry()
     */
    public static CountryCode getByLocale(Locale locale) {
        if (locale == null) {
            return null;
        }

        // Locale.getCountry() returns an uppercase ISO 3166 2-letter code.
        String country = locale.getCountry();

        if (country == null || country.length() == 0) {
            return CountryCode.UNDEFINED;
        }

        return getByCode(country, true);
    }

    /**
     * Canonicalize the given country code.
     *
     * @param code          ISO 3166-1 alpha-2 or alpha-3 country code.
     * @param caseSensitive {@code true} if the code should be handled case-sensitively.
     * @return If {@code code} is {@code null} or an empty string,
     * {@code null} is returned.
     * Otherwise, if {@code caseSensitive} is {@code true},
     * {@code code} is returned as is.
     * Otherwise, {@code code.toUpperCase()} is returned.
     */
    static String canonicalize(String code, boolean caseSensitive) {
        if (code == null || code.length() == 0) {
            return null;
        }

        if (caseSensitive) {
            return code;
        } else {
            return code.toUpperCase();
        }
    }

    public static CountryCode getByAlpha2Code(String code) {
        try {
            return Enum.valueOf(CountryCode.class, code);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static CountryCode getByAlpha3Code(String code) {
        return alpha3Map.get(code);
    }

    /**
     * Get a {@code CountryCode} that corresponds to the given alpha-4 code.
     *
     * <p>
     * Recognized alpha-4 codes are listed in the table below.
     * </p>
     *
     * @param code Alpha-4 code.
     * @return A {@code CountryCode} instance, or {@code null} if not found.
     */
    public static CountryCode getByAlpha4Code(String code) {
        return alpha4Map.get(code);
    }

    public static CountryCode getByCode(int code) {
        if (code <= 0) {
            return null;
        }

        return numericMap.get(code);
    }

    /**
     * Get a list of {@code CountryCode} by a name regular expression.
     *
     * <p>
     * This method is almost equivalent to {@link #findByName(Pattern)
     * findByName}{@code (Pattern.compile(regex))}.
     * </p>
     *
     * @param regex Regular expression for names.
     * @return List of {@code CountryCode}. If nothing has matched,
     * an empty list is returned.
     * @throws IllegalArgumentException               {@code regex} is {@code null}.
     * @throws java.util.regex.PatternSyntaxException {@code regex} failed to be compiled.
     */
    public static List<CountryCode> findByName(String regex) {
        if (regex == null) {
            throw new IllegalArgumentException("regex is null.");
        }

        // Compile the regular expression. This may throw
        // java.util.regex.PatternSyntaxException.
        Pattern pattern = Pattern.compile(regex);

        return findByName(pattern);
    }

    /**
     * Get a list of {@code CountryCode} by a name pattern.
     *
     * <p>
     * For example, the list obtained by the code snippet below:
     * </p>
     *
     * <pre style="background-color: #EEEEEE; margin-left: 2em; margin-right: 2em; border: 1px solid black; padding: 0.5em;">
     * Pattern pattern = Pattern.compile(<span style="color: darkred;">".*United.*"</span>);
     * List&lt;CountryCode&gt; list = CountryCode.findByName(pattern);</pre>
     *
     * <p>
     * contains 6 {@code CountryCode}s as listed below.
     * </p>
     *
     * <ol>
     * <li>{@link #AE} : United Arab Emirates
     * <li>{@link #GB} : United Kingdom
     * <li>{@link #TZ} : Tanzania, United Republic of
     * <li>{@link #UK} : United Kingdom
     * <li>{@link #UM} : United States Minor Outlying Islands
     * <li>{@link #US} : United States
     * </ol>
     *
     * @param pattern Pattern to match names.
     * @return List of {@code CountryCode}. If nothing has matched,
     * an empty list is returned.
     * @throws IllegalArgumentException {@code pattern} is {@code null}.
     */
    public static List<CountryCode> findByName(Pattern pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null.");
        }

        List<CountryCode> list = new ArrayList<>();

        for (CountryCode entry : values()) {
            // If the name matches the given pattern.
            if (pattern.matcher(entry.getName()).matches()) {
                list.add(entry);
            }
        }

        return list;
    }

    /**
     * Get the country name.
     *
     * @return The country name.
     */
    public String getName() {
        return name;
    }
    public String getAlpha2() {
        return name();
    }

    public String getAlpha3() {
        return alpha3;
    }

    public int getNumeric() {
        return numeric;
    }
    /**
     * Convert this {@code CountryCode} instance to a {@link Locale} instance.
     *
     * <p>
     * In most cases, this method creates a new {@code Locale} instance
     * every time it is called, but some {@code CountryCode} instances return
     * their corresponding entries in {@code Locale} class. For example,
     * {@link #CA CountryCode.CA} always returns {@link Locale#CANADA}.
     * </p>
     *
     * <p>
     * The table below lists {@code CountryCode} entries whose {@code toLocale()}
     * do not create new Locale instances but return entries in
     * {@code Locale} class.
     * </p>
     * <p>
     * In addition, {@code toLocale()} of {@link CountryCode#UNDEFINED
     * CountryCode.UNDEFINED} behaves a bit differently. It returns
     * {@link Locale#ROOT Locale.ROOT} when it is available (i.e. when
     * the version of Java SE is 1.6 or higher). Otherwise, it returns
     * a {@code Locale} instance whose language and country are empty
     * strings. Even in the latter case, the same instance is returned
     * on every call.
     * </p>
     *
     * @return A {@code Locale} instance that matches this {@code CountryCode}.
     */
    public Locale toLocale() {
        return new Locale("", name());
    }

    /**
     * Get the currency.
     *
     * <p>
     * This method is an alias of {@link Currency}{@code .}{@link
     * Currency#getInstance(Locale) getInstance}{@code (}{@link
     * #toLocale()}{@code )}. The only difference is that this method
     * returns {@code null} when {@code Currency.getInstance(Locale)}
     * throws {@code IllegalArgumentException}.
     * </p>
     *
     * <p>
     * This method returns {@code null} when the territory represented by
     * this {@code CountryCode} instance does not have a currency.
     * {@link #AQ} (Antarctica) is one example.
     * </p>
     *
     * <p>
     * In addition, this method returns {@code null} also when the ISO 3166
     * code represented by this {@code CountryCode} instance is not
     * supported by the implementation of {@link
     * Currency#getInstance(Locale)}. At the time of this writing,
     * {@link #SS} (South Sudan) is one example.
     * </p>
     *
     * @return A {@code Currency} instance. In some cases, null
     * is returned.
     * @see Currency#getInstance(Locale)
     */
    public Currency getCurrency() {
        try {
            return Currency.getInstance(toLocale());
        } catch (IllegalArgumentException e) {
            // Currency.getInstance(Locale) throws IllegalArgumentException
            // when the given ISO 3166 code is not supported.
            return null;
        }
    }
}
