package org.bardframework.commons.utils.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * ISO 639-2
 * language code (3-letter lowercase code).
 * <p>
 * Most languages have just one ISO 639-2 code, but there are some languages
 * that have 2 codes, ISO 639-2/T code ("terminological" code) and ISO 639-2/B
 * code ("bibliographic" code). The table below lists up langueses having two  ISO 639-2 codes.
 * <p>
 * ISO 639-2 code for <a href="http://en.wikipedia.org/wiki/Newari_language"
 * >Newari</a> is {@code new}, but in this enum, the corresponding entry
 * is not {@code new} but {@link #New} (the first letter is capital),
 * because {@code new} is a special word for Java programming language.
 */
public enum LanguageAlpha3Code {
    /**
     * Undefined.
     *
     * <p>
     * This is not an official ISO 639-2 code.
     * </p>
     *
     * @see #und und: Undetermined
     * @see #zxx zxx: No linguistic content
     */
    undefined("Undefined") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.undefined;
        }
    },

    /**
     * Afar
     * ({@link LanguageCode#aa aa}).
     */
    aar("Afar") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.aa;
        }
    },

    /**
     * Austro-Asiatic languages
     */
    aav("Austro-Asiatic languages"),

    /**
     * Abkhaz
     * ({@link LanguageCode#ab ab}).
     */
    abk("Abkhaz") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ab;
        }
    },

    /**
     * Achinese
     */
    ace("Achinese"),

    /**
     * Acoli
     */
    ach("Acoli"),

    /**
     * Adangme
     */
    ada("Adangme"),

    /**
     * Adyghe
     */
    ady("Adyghe"),

    /**
     * Afro-Asiatic languages
     */
    afa("Afro-Asiatic languages"),

    /**
     * Afrihili
     */
    afh("Afrihili"),

    /**
     * Afrikaans
     * ({@link LanguageCode#af af}).
     */
    afr("Afrikaans") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.af;
        }
    },

    /**
     * Ainu (Japan)
     */
    ain("Ainu (Japan)"),

    /**
     * Akan
     * ({@link LanguageCode#ak ak}).
     */
    aka("Akan") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ak;
        }
    },

    /**
     * Akkadian
     */
    akk("Akkadian"),

    /**
     * Albanian
     * ({@link LanguageCode#sq sq}) for bibliographic applications.
     *
     * @see #sqi
     */
    alb("Albanian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sq;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return sqi;
        }
    },

    /**
     * Aleut
     */
    ale("Aleut"),

    /**
     * Algonquian languages
     */
    alg("Algonquian languages"),

    /**
     * Southern Altai
     */
    alt("Southern Altai"),

    alv("Atlantic-Congo languages"),

    /**
     * Amharic
     * ({@link LanguageCode#am am}).
     */
    amh("Amharic") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.am;
        }
    },

    /**
     * Old English (ca&#0046; 450-1100)
     */
    ang("Old English"),

    /**
     * Angika
     */
    anp("Angika"),

    /**
     * Apache languages
     */
    apa("Apache languages"),

    /**
     * Alacalufan languages
     */
    aqa("Alacalufan languages"),

    /**
     * Algic languages
     */
    aql("Algic languages"),

    /**
     * Arabic
     * ({@link LanguageCode#ar ar}).
     */
    ara("Arabic") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ar;
        }
    },

    /**
     * Official Aramaic (700-300 BCE)
     */
    arc("Official Aramaic"),

    /**
     * Aragonese
     * ({@link LanguageCode#an an}).
     */
    arg("Aragonese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.an;
        }
    },

    /**
     * Armenian
     * ({@link LanguageCode#hy hy}) for bibliographic applications.
     *
     * @see #hye
     */
    arm("Armenian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.hy;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return hye;
        }
    },

    /**
     * Mapudungun
     */
    arn("Mapudungun"),

    /**
     * Arapaho
     */
    arp("Arapaho"),

    /**
     * Artificial languages
     */
    art("Artificial languages"),

    /**
     * Arawak
     */
    arw("Arawak"),

    /**
     * Assamese
     * ({@link LanguageCode#as as}).
     */
    asm("Assamese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.as;
        }
    },

    /**
     * Asturian
     */
    ast("Asturian"),

    /**
     * Athapascan languages
     */
    ath("Athapascan languages"),

    /**
     * Arauan languages
     */
    auf("Arauan languages"),

    /**
     * Australian languages
     */
    aus("Australian languages"),

    /**
     * Avaric
     * ({@link LanguageCode#av av}).
     */
    ava("Avaric") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.av;
        }
    },

    /**
     * Avestan
     * ({@link LanguageCode#ae ae}).
     */
    ave("Avestan") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ae;
        }
    },

    /**
     * Awadhi
     */
    awa("Awadhi"),

    /**
     * Arawakan languages
     */
    awd("Arawakan languages"),

    /**
     * Aymara
     * ({@link LanguageCode#ay ay}).
     */
    aym("Aymara") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ay;
        }
    },

    /**
     * Uto-Aztecan languages
     */
    azc("Uto-Aztecan languages"),

    /**
     * <a
     * href="http://en.wikipedia.org/wiki/Azerbaijani_language">Azerbaijani</a>
     * ({@link LanguageCode#az az}).
     */
    aze("Azerbaijani") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.az;
        }
    },

    /**
     * Banda languages
     */
    bad("Banda languages"),

    /**
     * Bamileke languages
     */
    bai("Bamileke languages"),

    /**
     * Bashkir
     * ({@link LanguageCode#ba ba}).
     */
    bak("Bashkir") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ba;
        }
    },

    /**
     * Baluchi
     */
    bal("Baluchi"),

    /**
     * Bambara
     * ({@link LanguageCode#bm bm}).
     */
    bam("Bambara") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bm;
        }
    },

    /**
     * Balinese
     */
    ban("Balinese"),

    /**
     * Basque
     * ({@link LanguageCode#eu eu}) for bibliographic applications.
     *
     * @see #eus
     */
    baq("Basque") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.eu;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return eus;
        }
    },

    /**
     * Basa (Cameroon)
     */
    bas("Basa (Cameroon)"),

    /**
     * Baltic languages
     */
    bat("Baltic languages"),

    /**
     * Beja
     */
    bej("Beja"),

    /**
     * Belarusian
     * ({@link LanguageCode#be be}).
     */
    bel("Belarusian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.be;
        }
    },

    /**
     * Bemba (Zambia)
     */
    bem("Bemba (Zambia)"),

    /**
     * Bengali
     * ({@link LanguageCode#bn bn}).
     */
    ben("Bengali") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bn;
        }
    },

    /**
     * Berber languages
     */
    ber("Berber languages"),

    /**
     * Bhojpuri
     */
    bho("Bhojpuri"),

    /**
     * Bihari
     * ({@link LanguageCode#bh bh}).
     */
    bih("Bihari languages") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bh;
        }
    },

    /**
     * Bikol
     */
    bik("Bikol"),

    /**
     * Bini
     */
    bin("Bini"),

    /**
     * Bislama
     * ({@link LanguageCode#bi bi}).
     */
    bis("Bislama") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bi;
        }
    },

    /**
     * Siksika
     */
    bla("Siksika"),

    /**
     * Bantu languages
     */
    bnt("Bantu languages"),

    /**
     * Tibetan
     * ({@link LanguageCode#bo bo}) for terminology applications.
     *
     * @see #tib
     */
    bod("Tibetan") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bo;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return tib;
        }
    },

    /**
     * Bosnian
     * ({@link LanguageCode#bs bs}).
     */
    bos("Bosnian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bs;
        }
    },

    /**
     * Braj
     */
    bra("Braj"),

    /**
     * Breton
     * ({@link LanguageCode#br br}).
     */
    bre("Breton") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.br;
        }
    },

    /**
     * Batak languages
     */
    btk("Batak languages"),

    /**
     * Buriat
     */
    bua("Buriat"),

    /**
     * Buginese
     */
    bug("Buginese"),

    /**
     * Bulgarian
     * ({@link LanguageCode#bg bg}).
     */
    bul("Bulgarian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bg;
        }
    },

    /**
     * Burmese
     * ({@link LanguageCode#my my}) for bibliographic applications.
     *
     * @see #mya
     */
    bur("Burmese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.my;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return mya;
        }
    },

    /**
     * Bilin
     */
    byn("Bilin"),

    /**
     * Caddo
     */
    cad("Caddo"),

    /**
     * Central American Indian languages
     */
    cai("Central American Indian languages"),

    /**
     * Galibi Carib
     */
    car("Galibi Carib"),

    /**
     * Catalan
     * ({@link LanguageCode#ca ca}).
     */
    cat("Catalan") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ca;
        }
    },

    /**
     * Caucasian languages
     */
    cau("Caucasian languages"),

    /**
     * Chibchan languages
     */
    cba("Chibchan languages"),

    /**
     * North Caucasian languages
     */
    ccn("North Caucasian languages"),

    /**
     * South Caucasian languages
     */
    ccs("South Caucasian languages"),

    /**
     * Chadic languages
     */
    cdc("Chadic languages"),

    /**
     * Caddoan languages
     */
    cdd("Caddoan languages"),

    /**
     * Cebuano
     */
    ceb("Cebuano"),

    /**
     * Celtic languages
     */
    cel("Celtic languages"),

    /**
     * Czech
     * ({@link LanguageCode#cs cs}) for terminology applications.
     *
     * @see #cze
     */
    ces("Czech") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cs;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return cze;
        }
    },

    /**
     * Chamorro
     * ({@link LanguageCode#ch ch}).
     */
    cha("Chamorro") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ch;
        }
    },

    /**
     * Chibcha
     */
    chb("Chibcha"),

    /**
     * Chechen
     * ({@link LanguageCode#ce ce}).
     */
    che("Chechen") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ce;
        }
    },

    /**
     * Chagatai
     */
    chg("Chagatai"),

    /**
     * Chinese
     * ({@link LanguageCode#zh zh}) for bibliographic applications.
     *
     * @see #zho
     */
    chi("Chinese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.zh;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return zho;
        }
    },

    /**
     * Chuukese
     */
    chk("Chuukese"),

    /**
     * Mari (Russia)
     */
    chm("Mari (Russia)"),

    /**
     * Chinook jargon
     */
    chn("Chinook jargon"),

    /**
     * Choctaw
     */
    cho("Choctaw"),

    /**
     * Chipewyan
     */
    chp("Chipewyan"),

    /**
     * Cherokee
     */
    chr("Cherokee"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Old_Church_Slavonic">Church
     * Slavonic</a>
     * ({@link LanguageCode#cu cu}).
     */
    chu("Church Slavic") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cu;
        }
    },

    /**
     * Chuvash
     * ({@link LanguageCode#cv cv}).
     */
    chv("Chuvash") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cv;
        }
    },

    /**
     * Cheyenne
     */
    chy("Cheyenne"),

    /**
     * Chamic languages
     */
    cmc("Chamic languages"),

    /**
     * Coptic
     */
    cop("Coptic"),

    /**
     * Cornish
     * ({@link LanguageCode#kw kw}).
     */
    cor("Comish") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kw;
        }
    },

    /**
     * Corsican
     * ({@link LanguageCode#co co}).
     */
    cos("Corsican") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.co;
        }
    },

    /**
     * <a href="http://en.wikipedia.org/wiki/English_based_Creoles"
     * >English based Creoles</a> and
     * <a href="http://en.wikipedia.org/wiki/English_based_pidgins"
     * >pidgins</a>
     */
    cpe("English based Creoles and pidgins"),

    /**
     * <a href="http://en.wikipedia.org/wiki/French-Based_Creoles"
     * >French-Based Creoles</a> and
     * <a href="http://en.wikipedia.org/wiki/French-Based_pidgins"
     * >pidgins</a>
     */
    cpf("French-Based Creoles and pidgins"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Portuguese-Based_Creoles"
     * >Portuguese-Based Creoles</a> and
     * <a href="http://en.wikipedia.org/wiki/Portuguese-Based_pidgins"
     * >pidgins</a>
     */
    cpp("Portuguese-Based Creoles and pidgins"),

    /**
     * Cree
     * ({@link LanguageCode#cr cr}).
     */
    cre("Cree") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cr;
        }
    },

    /**
     * Crimean Tatar
     */
    crh("Crimean Tatar"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Creole_language"
     * >Creoles</a> and
     * <a href="http://en.wikipedia.org/wiki/Pidgin_language"
     * >pidgins</a>
     */
    crp("Creoles and pidgins"),

    /**
     * Kashubian
     */
    csb("Kashubian"),

    /**
     * Central Sudanic languages
     */
    csu("Central Sudanic languages"),

    /**
     * Cushitic languages
     */
    cus("Cushitic languages"),

    /**
     * Welsh
     * ({@link LanguageCode#cy cy}) for terminology applications.
     *
     * @see #wel
     */
    cym("Welsh") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cy;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return wel;
        }
    },

    /**
     * Czech
     * ({@link LanguageCode#cs cs}) for bibliographic applications.
     *
     * @see #ces
     */
    cze("Czech") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cs;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return ces;
        }
    },

    /**
     * Dakota
     */
    dak("Dakota"),

    /**
     * Danish
     * {@link LanguageCode#da da}).
     */
    dan("Danish") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.da;
        }
    },

    /**
     * Dargwa
     */
    dar("Dargwa"),

    /**
     * Land Dayak languages
     */
    day("Land Dayak languages"),

    /**
     * Delaware
     */
    del("Delaware"),

    /**
     * Slave (Athapascan)
     */
    den("Slave (Athapascan)"),

    /**
     * German
     * ({@link LanguageCode#de de}) for terminology applications.
     *
     * @see #ger
     */
    deu("German") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.de;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return ger;
        }
    },

    /**
     * Dogrib
     */
    dgr("Dogrib"),

    /**
     * Dinka
     */
    din("Dinka"),

    /**
     * Dhivehi
     * ({@link LanguageCode#dv dv}).
     */
    div("Dhivehi") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.dv;
        }
    },

    /**
     * Mande languages
     */
    dmn("Mande languages"),

    /**
     * Dogri (macrolanguage)
     */
    doi("Dogri"),

    /**
     * Dravidian languages
     */
    dra("Dravidian languages"),

    /**
     * Lower Sorbian
     */
    dsb("Lower Sorbian"),

    /**
     * Duala
     */
    dua("Duala"),

    /**
     * Middle Dutch (ca. 1050-1350)
     */
    dum("Middle Dutch"),

    /**
     * Dutch
     * ({@link LanguageCode#nl nl}) for bibliography applications.
     *
     * @see #nld
     */
    dut("Dutch") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nl;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return nld;
        }
    },

    /**
     * Dyula
     */
    dyu("Dyula"),

    /**
     * Dzongkha
     * ({@link LanguageCode#dz dz}).
     */
    dzo("Dzongkha") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.dz;
        }
    },

    /**
     * Efik
     */
    efi("Efik"),

    /**
     * Egyptian languages
     */
    egx("Egyptian languages"),

    /**
     * Egyptian (Ancient)
     */
    egy("Egyptian (Ancient)"),

    /**
     * Ekajuk
     */
    eka("Ekajuk"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Modern_Greek_language"
     * >Modern Greek</a> (1453-)
     * ({@link LanguageCode#el el}) for terminology applications.
     *
     * @see #gre Modern Greek (gre)
     * @see #grc Acient Greek (grc)
     */
    ell("Modern Greek") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.el;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return gre;
        }
    },

    /**
     * Elamite
     */
    elx("Elamite"),

    /**
     * English
     * ({@link LanguageCode#en en}).
     */
    eng("English") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.en;
        }
    },

    /**
     * Middle English (1100-1500)
     */
    enm("Middle English"),

    /**
     * Esperanto
     * ({@link LanguageCode#eo eo}).
     */
    epo("Esperanto") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.eo;
        }
    },

    /**
     * Estonian
     * ({@link LanguageCode#et et}).
     */
    est("Estonian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.et;
        }
    },

    /**
     * Eskimo-Aleut languages
     */
    esx("Eskimo-Aleut languages"),

    /**
     * Basque (family)
     */
    euq("Basque"),

    /**
     * Basque (family)
     * ({@link LanguageCode#eu eu}) for terminology applications.
     *
     * @see #baq
     */
    eus("Basque (family)") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.eu;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return baq;
        }
    },

    /**
     * Ewe
     * ({@link LanguageCode#ee ee}).
     */
    ewe("Ewe") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ee;
        }
    },

    /**
     * Ewondo
     */
    ewo("Ewondo"),

    /**
     * Fang (Equatorial Guinea)
     */
    fan("Fang (Equatorial Guinea)"),

    /**
     * Faroese
     * ({@link LanguageCode#fo fo}).
     */
    fao("Faroese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fo;
        }
    },

    /**
     * Persian
     * ({@link LanguageCode#fa fa}) for terminology applications.
     *
     * @see #per
     */
    fas("Persian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fa;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return per;
        }
    },

    /**
     * Fanti
     */
    fat("Fanti"),

    /**
     * Fijian
     * ({@link LanguageCode#fj fj}).
     */
    fij("Fijian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fj;
        }
    },

    /**
     * Filipino
     */
    fil("Filipino"),

    /**
     * Finnish
     * ({@link LanguageCode#fi fi}).
     */
    fin("Finnish") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fi;
        }
    },

    /**
     * Finno-Ugrian languages
     */
    fiu("Finno-Ugrian languages"),

    /**
     * Fon
     */
    fon("Fon"),

    /**
     * Formosan languages
     */
    fox("Formosan languages"),

    /**
     * French
     * ({@link LanguageCode#fr fr}) for terminology applications.
     *
     * @see #fre
     */
    fra("French") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fr;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return fre;
        }
    },

    /**
     * French
     * ({@link LanguageCode#fr fr}) for bibliographic applications.
     *
     * @see #fra
     */
    fre("French") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fr;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return fra;
        }
    },

    /**
     * Middle French (ca&#0046; 1400-1600)
     */
    frm("Middle French"),

    /**
     * Old French (842-ca&#0046; 1400)
     */
    fro("Old French"),

    /**
     * Northern Frisian
     */
    frr("Northern Frisian"),

    /**
     * Eastern Frisian
     */
    frs("Eastern Frisian"),

    /**
     * <a href="http://en.wikipedia.org/wiki/West_Frisian_language">West
     * Frisian</a>
     * ({@link LanguageCode#fy fy}).
     */
    fry("West Frisian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fy;
        }
    },

    /**
     * Fula
     * ({@link LanguageCode#ff ff}).
     */
    ful("Fula") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ff;
        }
    },

    /**
     * Friulian
     */
    fur("Friulian"),

    /**
     * Ga
     */
    gaa("Ga"),

    /**
     * Gayo
     */
    gay("Gayo"),

    /**
     * Gbaya (Central African Republic)
     */
    gba("Gbaya (Central African Republic)"),

    /**
     * Germanic languages
     */
    gem("Germanic languages"),

    /**
     * Georgian
     * ({@link LanguageCode#ka ka}) for bibliographic applications.
     *
     * @see #kat
     */
    geo("Georgian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ka;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return kat;
        }
    },

    /**
     * German
     * ({@link LanguageCode#de de}) for bibliographic applications.
     *
     * @see #deu
     */
    ger("German") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.de;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return deu;
        }
    },

    /**
     * Geez
     */
    gez("Geez"),

    /**
     * Gilbertese
     */
    gil("Gilbertese"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Scottish_Gaelic_language">Scottish
     * Gaelic</a>
     * ({@link LanguageCode#gd gd}).
     */
    gla("Scottish Gaelic") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.gd;
        }
    },

    /**
     * Irish
     * ({@link LanguageCode#ga ga}).
     */
    gle("Irish") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ga;
        }
    },

    /**
     * Galician
     * ({@link LanguageCode#gl gl}).
     */
    glg("Galician") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.gl;
        }
    },

    /**
     * Manx
     * ({@link LanguageCode#gv gv}).
     */
    glv("Manx") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.gv;
        }
    },

    /**
     * East Germanic languages
     */
    gme("East Germanic languages"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Middle_High_German_language"
     * >Middle High German</a> (ca&#0046; 1050-1500)
     */
    gmh("Middle High German"),

    /**
     * <a href="http://en.wikipedia.org/wiki/North_Germanic_languages"
     * >North Germanic languages</a>
     */
    gmq("North Germanic languages"),

    /**
     * <a href="http://en.wikipedia.org/wiki/West_Germanic_languages"
     * >West Germanic languages</a>
     */
    gmw("West Germanic languages"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Old_High_German_language"
     * >Old High German</a> (ca&#0046; 750-1050)
     */
    goh("Old High German"),

    /**
     * Gondi
     */
    gon("Gondi"),

    /**
     * Gorontalo
     */
    gor("Gorontalo"),

    /**
     * Gothic
     */
    got("Gothic"),

    /**
     * Grebo
     */
    grb("Grebo"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Ancient_Greek_language"
     * >Ancient Greek</a> (to 1453)
     *
     * @see #ell Modern Greek (ell)
     */
    grc("Ancient Greek"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Modern_Greek_language"
     * >Modern Greek</a> (1453-)
     * ({@link LanguageCode#el el}) for bibliographic applications.
     *
     * @see #ell Modern Greek (ell)
     * @see #grc Acient Greek (grc)
     */
    gre("Modern Greek") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.el;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return ell;
        }
    },

    /**
     * Greek languages
     */
    grk("Greek languages"),

    /**
     * ({@link LanguageCode#gn gn}).
     */
    grn("Guaran\u00ED") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.gn;
        }
    },

    /**
     * Swiss German
     */
    gsw("Swiss German"),

    /**
     * Gujarati
     * ({@link LanguageCode#gu gu}).
     */
    guj("Gujarati") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.gu;
        }
    },

    /**
     * Gwich&#x2bc;in
     */
    gwi("Gwich\u02BCin"),

    /**
     * Haida
     */
    hai("Haida"),

    /**
     * <a
     * href="http://en.wikipedia.org/wiki/Haitian_Creole_language">Haitian</a>
     * ({@link LanguageCode#ht ht}).
     */
    hat("Haitian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ht;
        }
    },

    /**
     * Hausa
     * ({@link LanguageCode#ha ha}).
     */
    hau("Hausa") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ha;
        }
    },

    /**
     * Hawaiian
     */
    haw("Hawaiian"),

    /**
     * Hebrew
     * ({@link LanguageCode#he he}).
     */
    heb("Hebrew") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.he;
        }
    },

    /**
     * Herero
     * ({@link LanguageCode#hz hz}).
     */
    her("Herero") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.hz;
        }
    },

    /**
     * Hiligaynon
     */
    hil("Hiligaynon"),

    /**
     * Himachali languages
     */
    him("Himachali languages"),

    /**
     * Hindi
     * ({@link LanguageCode#hi hi}).
     */
    hin("Hindi") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.hi;
        }
    },

    /**
     * Hittite
     */
    hit("Hittite"),

    /**
     * Hmong
     */
    hmn("Hmong"),

    /**
     * Hiri Motu
     * ({@link LanguageCode#ho ho}).
     */
    hmo("Hiri Motu") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ho;
        }
    },

    /**
     * Hmong-Mien languages
     */
    hmx("Hmong-Mien languages"),

    /**
     * Hokan languages
     */
    hok("Hokan languages"),

    /**
     * Croatian
     * ({@link LanguageCode#hr hr}).
     */
    hrv("Croatian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.hr;
        }
    },

    /**
     * Upper Sorbian
     */
    hsb("Upper Sorbian"),

    /**
     * Hungarian
     * ({@link LanguageCode#hu hu}).
     */
    hun("Hungarian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.hu;
        }
    },

    /**
     * Hupa
     */
    hup("Hupa"),

    /**
     * Armenian
     * ({@link LanguageCode#hy hy}) for terminology applications.
     *
     * @see #arm
     */
    hye("Armenian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.hy;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return arm;
        }
    },

    /**
     * Armenian (family)
     */
    hyx("Armenian (family)"),

    /**
     * Iban
     */
    iba("Iban"),

    /**
     * Igbo
     * ({@link LanguageCode#ig ig}).
     */
    ibo("Igbo") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ig;
        }
    },

    /**
     * Icelandic
     * ({@link LanguageCode#is is}) for biblioraphic applications.
     *
     * @see #isl
     */
    ice("Icelandic") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.is;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return isl;
        }
    },

    /**
     * Ido
     * ({@link LanguageCode#io io}).
     */
    ido("Ido") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.io;
        }
    },

    /**
     * Nuosu
     * ({@link LanguageCode#ii ii}).
     */
    iii("Nuosu") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ii;
        }
    },

    /**
     * Indo-Iranian languages
     */
    iir("Indo-Iranian languages"),

    /**
     * Ijo languages
     */
    ijo("Ijo languages"),

    /**
     * Inuktitut
     * ({@link LanguageCode#iu iu}).
     */
    iku("Inuktitut") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.iu;
        }
    },

    /**
     * <a
     * href="http://en.wikipedia.org/wiki/Interlingue_language">Interlingue</a>
     * ({@link LanguageCode#ie ie}).
     */
    ile("Interlingue") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ie;
        }
    },

    /**
     * Iloko
     */
    ilo("Iloko"),

    /**
     * Interlingua
     * {@link LanguageCode#ia ia}).
     */
    ina("Interlingua") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ia;
        }
    },

    /**
     * <a href="http://en.wikipedia.org/wiki/Interlingua_language_(International_Auxiliary_Language_Association)"
     * >Interlingua (International Auxiliary Language Association)</a>
     */
    inc("Interlingua (International Auxiliary Language Association)"),

    /**
     * Indonesian
     * ({@link LanguageCode#id id}).
     */
    ind("Indonesian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.id;
        }
    },

    /**
     * Indo-European languages
     */
    ine("Indo-European languages"),

    /**
     * Ingush
     */
    inh("Ingush"),

    /**
     * Inupiaq
     * ({@link LanguageCode#ik ik}).
     */
    ipk("Inupiaq") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ik;
        }
    },

    /**
     * Iranian languages
     */
    ira("Iranian languages"),

    /**
     * Iroquoian languages
     */
    iro("Iroquoian languages"),

    /**
     * Icelandic
     * ({@link LanguageCode#is is}) for terminology applications.
     *
     * @see #ice
     */
    isl("Icelandic") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.is;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return ice;
        }
    },

    /**
     * Italian
     * ({@link LanguageCode#it it}).
     */
    ita("Italian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.it;
        }
    },

    /**
     * Italic languages
     */
    itc("Italic languages"),

    /**
     * Javanese
     * ({@link LanguageCode#jv jv}).
     */
    jav("Javanese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.jv;
        }
    },

    /**
     * Lojban
     */
    jbo("Lojban"),

    /**
     * Japanese
     * ({@link LanguageCode#ja ja}).
     */
    jpn("Japanese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ja;
        }
    },

    /**
     * Judeo-Persian
     */
    jpr("Judeo-Persian"),

    /**
     * Japanese (family)
     */
    jpx("Japanese (family)"),

    /**
     * Judeo-Arabic
     */
    jrb("Judeo-Arabic"),

    /**
     * Kara-Kalpak
     */
    kaa("Kara-Kalpak"),

    /**
     * Kabyle
     */
    kab("Kabyle"),

    /**
     * Kachin
     */
    kac("Kachin"),

    /**
     * <a
     * href="http://en.wikipedia.org/wiki/Kalaallisut_language">Kalaallisut</a>
     * ({@link LanguageCode#kl kl}).
     */
    kal("Kalaallisut") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kl;
        }
    },

    /**
     * Kamba (Kenya)
     */
    kam("Kamba (Kenya)"),

    /**
     * Kannada
     * ({@link LanguageCode#kn kn}).
     */
    kan("Kannada") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kn;
        }
    },

    /**
     * Karen languages
     */
    kar("Karen languages"),

    /**
     * Kashmiri
     * ({@link LanguageCode#ks ks}).
     */
    kas("Kashmiri") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ks;
        }
    },

    /**
     * Georgian
     * ({@link LanguageCode#ka ka}) for terminology applications.
     *
     * @see #geo
     */
    kat("Georgian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ka;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return geo;
        }
    },

    /**
     * Kanuri
     * ({@link LanguageCode#kr kr}).
     */
    kau("Kanuri") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kr;
        }
    },

    /**
     * Kawi
     */
    kaw("Kawi"),

    /**
     * Kazakh
     * ({@link LanguageCode#kk kk}).
     */
    kaz("Kazakh") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kk;
        }
    },

    /**
     * Kabardian
     */
    kbd("Kabardian"),

    /**
     * Kordofanian languages
     */
    kdo("Kordofanian languages"),

    /**
     * Khasi
     */
    kha("Khasi"),

    /**
     * Khoisan languages
     */
    khi("Khoisan languages"),

    /**
     * Khmer
     * ({@link LanguageCode#km km}).
     */
    khm("Central Khmer") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.km;
        }
    },

    /**
     * Khotanese
     */
    kho("Khotanese"),

    /**
     * Kikuyu
     * ({@link LanguageCode#ki ki}).
     */
    kik("Kikuyu") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ki;
        }
    },

    /**
     * Kinyarwanda
     * ({@link LanguageCode#rw rw}).
     */
    kin("Kinyarwanda") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.rw;
        }
    },

    /**
     * Kyrgyz
     * ({@link LanguageCode#ky ky}).
     */
    kir("Kirghiz") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ky;
        }
    },

    /**
     * Kimbundu
     */
    kmb("Kimbundu"),

    /**
     * Konkani (macrolanguage)
     */
    kok("Konkani"),

    /**
     * Komi
     * ({@link LanguageCode#kv kv}).
     */
    kom("Komi") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kv;
        }
    },

    /**
     * Kongo
     * ({@link LanguageCode#kg kg}).
     */
    kon("Kongo") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kg;
        }
    },

    /**
     * Korean
     * ({@link LanguageCode#ko ko}).
     */
    kor("Korean") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ko;
        }
    },

    /**
     * Kosraean
     */
    kos("Kosraean"),

    /**
     * Kpelle
     */
    kpe("Kpelle"),

    /**
     * Karachay-Balkar
     */
    krc("Karachay-Balkar"),

    /**
     * Karelian
     */
    krl("Karelian"),

    /**
     * Kru languages
     */
    kro("Kru languages"),

    /**
     * Kurukh
     */
    kru("Kurukh"),

    /**
     * Kwanyama
     * ({@link LanguageCode#kj kj}).
     */
    kua("Kuanyama") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.kj;
        }
    },

    /**
     * Kumyk
     */
    kum("Kumyk"),

    /**
     * Kurdish
     * ({@link LanguageCode#ku ku}).
     */
    kur("Kurdish") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ku;
        }
    },

    /**
     * Kutenai
     */
    kut("Kutenai"),

    /**
     * Ladino
     */
    lad("Ladino"),

    /**
     * Lahnda
     */
    lah("Lahnda"),

    /**
     * Lamba
     */
    lam("Lamba"),

    /**
     * Lao
     * ({@link LanguageCode#lo lo}).
     */
    lao("Lao") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.lo;
        }
    },

    /**
     * Latin
     * ({@link LanguageCode#la la}).
     */
    lat("Latin") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.la;
        }
    },

    /**
     * Latvian
     * ({@link LanguageCode#lv lv}).
     */
    lav("Latvian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.lv;
        }
    },

    /**
     * Lezghian
     */
    lez("Lezghian"),

    /**
     * Limburgish
     * ({@link LanguageCode#li li}).
     */
    lim("Limburgan") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.li;
        }
    },

    /**
     * Lingala
     * ({@link LanguageCode#ln ln}).
     */
    lin("Lingala") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ln;
        }
    },

    /**
     * Lithuanian
     * ({@link LanguageCode#lt lt}).
     */
    lit("Lithuanian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.lt;
        }
    },

    /**
     * Mongo
     */
    lol("Mongo"),

    /**
     * Lozi
     */
    loz("Lozi"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Luxembourgish_language">
     * Luxembourgish</a>
     * ({@link LanguageCode#lb lb}).
     */
    ltz("Luxembourgish") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.lb;
        }
    },

    /**
     * Luba-Lulua
     */
    lua("Luba-Lulua"),

    /**
     * Luba-Katanga
     * ({@link LanguageCode#lu lu}).
     */
    lub("Luba-Katanga") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.lu;
        }
    },

    /**
     * Ganda
     * ({@link LanguageCode#lg lg}).
     */
    lug("Ganda") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.lg;
        }
    },

    /**
     * Luiseno
     */
    lui("Luiseno"),

    /**
     * Lunda
     */
    lun("Lunda"),

    /**
     * Luo (Kenya and Tanzania)
     */
    luo("Luo (Kenya and Tanzania)"),

    /**
     * Lushai
     */
    lus("Lushai"),

    /**
     * Macedonian
     * ({@link LanguageCode#mk mk}) for bibliographic applications.
     *
     * @see #mkd
     */
    mac("Macedonian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mk;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return mkd;
        }
    },

    /**
     * Madurese
     */
    mad("Madurese"),

    /**
     * Magahi
     */
    mag("Magahi"),

    /**
     * <a
     * href="http://en.wikipedia.org/wiki/Marshallese_language">Marshallese</a>
     * ({@link LanguageCode#mh mh}).
     */
    mah("Marshallese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mh;
        }
    },

    /**
     * Maithili
     */
    mai("Maithili"),

    /**
     * Makasar
     */
    mak("Makasar"),

    /**
     * Malayalam
     * ({@link LanguageCode#ml ml}).
     */
    mal("Malayalam") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ml;
        }
    },

    /**
     * Mandingo
     */
    man("Mandingo"),

    /**
     * M&#257;ori
     * ({@link LanguageCode#mi mi}) for bibliographic applications.
     *
     * @see #mri
     */
    mao("M\u0101ori") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mi;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return mri;
        }
    },

    /**
     * Austronesian languages
     */
    map("Austronesian languages"),

    /**
     * Marathi
     * ({@link LanguageCode#mr mr}).
     */
    mar("Marathi") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mr;
        }
    },

    /**
     * Masai
     */
    mas("Masai"),

    /**
     * Malay (macrolanguage)
     * ({@link LanguageCode#ms ms}) for bibliographic applications.
     *
     * @see #msa
     */
    may("Malay") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ms;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return msa;
        }
    },

    /**
     * Moksha
     */
    mdf("Moksha"),

    /**
     * Mandar
     */
    mdr("Mandar"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Mende_language_(Sierra_Leone)"
     * >Mende (Sierra Leone)</a>
     */
    men("Mende (Sierra Leone)"),

    /**
     * Middle Irish (900-1200)
     */
    mga("Middle Irish"),

    /**
     * Mi'kmaq
     */
    mic("Mi'kmaq"),

    /**
     * Minangkabau
     */
    min("Minangkabau"),

    /**
     * Uncoded languages.
     */
    mis("Uncoded languages"),

    /**
     * Macedonian
     * ({@link LanguageCode#mk mk}) for terminology applications.
     *
     * @see #mac
     */
    mkd("Macedonian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mk;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return mac;
        }
    },

    /**
     * Mon-Khmer languages
     */
    mkh("Mon-Khmer languages"),

    /**
     * Malagasy
     * ({@link LanguageCode#mg mg}).
     */
    mlg("Malagasy") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mg;
        }
    },

    /**
     * Maltese
     * ({@link LanguageCode#mt mt}).
     */
    mlt("Maltese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mt;
        }
    },

    /**
     * Manchu
     */
    mnc("Manchu"),

    /**
     * Manipuri
     */
    mni("Manipuri"),

    /**
     * Manobo languages
     */
    mno("Manobo languages"),

    /**
     * Mohawk
     */
    moh("Mohawk"),

    /**
     * Mongolian
     * ({@link LanguageCode#mn mn}).
     */
    mon("Mongolian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mn;
        }
    },

    /**
     * Mossi
     */
    mos("Mossi"),

    /**
     * M&#257;ori
     * ({@link LanguageCode#mi mi}) for terminology applications.
     *
     * @see #mao
     */
    mri("M\u0101ori") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.mi;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return mao;
        }
    },

    /**
     * Malay
     * ({@link LanguageCode#ms ms}) for terminology applications.
     *
     * @see #may
     */
    msa("Malay") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ms;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return may;
        }
    },

    /**
     * Multiple languages.
     */
    mul("Multiple languages"),

    /**
     * Munda languages
     */
    mun("Munda languages"),

    /**
     * Creek
     */
    mus("Creek"),

    /**
     * Mirandese
     */
    mwl("Mirandese"),

    /**
     * Marwari
     */
    mwr("Marwari"),

    /**
     * Burmese
     * ({@link LanguageCode#my my}) for terminology applications.
     *
     * @see #bur
     */
    mya("Burmese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.my;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return bur;
        }
    },

    /**
     * Mayan languages
     */
    myn("Mayan languages"),

    /**
     * Erzya
     */
    myv("Erzya"),

    /**
     * Nahuatl languages
     */
    nah("Nahuatl languages"),

    /**
     * <a href="http://en.wikipedia.org/wiki/North_American_Indian_languages"
     * >North American Indian</a>
     */
    nai("North American Indian"),

    /**
     * Neapolitan
     */
    nap("Neapolitan"),

    /**
     * Nauru
     * ({@link LanguageCode#na na}).
     */
    nau("Nauru") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.na;
        }
    },

    /**
     * Navajo
     * ({@link LanguageCode#nv nv}).
     */
    nav("Navajo") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nv;
        }
    },

    /**
     * <a href="http://en.wikipedia.org/wiki/Southern_Ndebele_language">Southern
     * Ndebele</a>
     * ({@link LanguageCode#nr nr}).
     */
    nbl("South Ndebele") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nr;
        }
    },

    /**
     * <a href="http://en.wikipedia.org/wiki/Northern_Ndebele_language">Northern
     * Ndebele</a>
     * ({@link LanguageCode#nd nd}).
     */
    nde("North Ndebele") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nd;
        }
    },

    /**
     * Ndonga
     * ({@link LanguageCode#ng ng}).
     */
    ndo("Ndonga") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ng;
        }
    },

    /**
     * Low German
     */
    nds("Low German"),

    /**
     * Nepali (macrolanguage)
     * ({@link LanguageCode#ne ne}).
     */
    nep("Nepali") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ne;
        }
    },

    /**
     * Newari
     *
     * <p>
     * Because {@code new} is a special word for Java programming
     * language, {@code new} cannot be used as an enum entry.
     * So, the first letter of this entry is a capital letter.
     * </p>
     *
     * <p>
     * {@code toString()} method of this instance ({@code New})
     * returns {@code "new"}.
     * </p>
     */
    New("Newari") {
        @Override
        public String toString() {
            return "new";
        }
    },

    /**
     * Trans-New Guinea languages
     */
    ngf("Trans-New Guinea languages"),

    /**
     * Nias
     */
    nia("Nias"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Niger-Kordofanian_languages"
     * >Niger-Kordofanian languages</a>
     */
    nic("Niger-Kordofanian languages"),

    /**
     * Niuean
     */
    niu("Niuean"),

    /**
     * Dutch
     * ({@link LanguageCode#nl nl}) for terminology applications.
     *
     * @see #dut
     */
    nld("Dutch") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nl;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return dut;
        }
    },

    /**
     * Norwegian Nynorsk
     * ({@link LanguageCode#nn nn}).
     */
    nno("Norwegian Nynorsk") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nn;
        }
    },

    /**
     * <a href="http://en.wikipedia.org/wiki/Bokm%C3%A5l">Norwegian
     * Bokm&aring;l</a>
     * ({@link LanguageCode#nb nb}).
     */
    nob("Norwegian Bokm\u00E5l") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.nb;
        }
    },

    /**
     * Nogai
     */
    nog("Nogai"),

    /**
     * Old Norse
     */
    non("Old Norse"),

    /**
     * Norwegian
     * ({@link LanguageCode#no no}).
     */
    nor("Norwegian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.no;
        }
    },

    /**
     * N'Ko
     */
    nqo("N'Ko"),

    /**
     * Pedi
     */
    nso("Pedi"),

    /**
     * Nubian languages
     */
    nub("Nubian languages"),

    /**
     * Classical Newari
     */
    nwc("Classical Newari"),

    /**
     * Chichewa
     * ({@link LanguageCode#ny ny}).
     */
    nya("Nyanja") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ny;
        }
    },

    /**
     * Nyamwezi
     */
    nym("Nyamwezi"),

    /**
     * Nyankole
     */
    nyn("Nyankole"),

    /**
     * Nyoro
     */
    nyo("Nyoro"),

    /**
     * <a href="Nzima">Nzima</a>
     */
    nzi("Nzima"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Occitan_language"
     * >Occitan</a> (post 1500)
     * ({@link LanguageCode#oc oc}).
     */
    oci("Occitan") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.oc;
        }
    },

    /**
     * Ojibwe
     * ({@link LanguageCode#oj oj}).
     */
    oji("Ojibwa") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.oj;
        }
    },

    /**
     * Oto-Manguean languages
     */
    omq("Oto-Manguean languages"),

    /**
     * Omotic languages
     */
    omv("Omotic languages"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Oriya_language"
     * >Oriya</a> (macrolanguage)
     * ({@link LanguageCode#or or}).
     */
    ori("Oriya") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.or;
        }
    },

    /**
     * Oromo
     * ({@link LanguageCode#om om}).
     */
    orm("Oromo") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.om;
        }
    },

    /**
     * Osage
     */
    osa("Osage"),

    /**
     * Ossetian
     * ({@link LanguageCode#os os}).
     */
    oss("Ossetian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.os;
        }
    },

    /**
     * <a href="http://en.wikipedia.org/wiki/Ottoman_Turkish_language"
     * >Ottoman Turkish</a> (1500-1928)
     */
    ota("Ottoman Turkish"),

    /**
     * Otomian languages
     */
    oto("Otomian languages"),

    /**
     * Papuan languages
     */
    paa("Papuan languages"),

    /**
     * Pangasinan
     */
    pag("Pangasinan"),

    /**
     * Pahlavi
     */
    pal("Pahlavi"),

    /**
     * Pampanga
     */
    pam("Pampanga"),

    /**
     * Punjabi
     * ({@link LanguageCode#pa pa}).
     */
    pan("Panjabi") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.pa;
        }
    },

    /**
     * Papiamento
     */
    pap("Papiamento"),

    /**
     * Palauan
     */
    pau("Palauan"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Old_Persian_language"
     * >Old Persian</a> (ca. 600-400 B.C.)
     */
    peo("Old Persian"),

    /**
     * Persian
     * ({@link LanguageCode#fa fa}) for bibliographic applications.
     *
     * @see #fas
     */
    per("Persian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.fa;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return fas;
        }
    },

    /**
     * Philippine languages
     */
    phi("Philippine languages"),

    /**
     * Phoenician
     */
    phn("Phoenician"),

    /**
     * Central Malayo-Polynesian languages
     */
    plf("Central Malayo-Polynesian languages"),

    /**
     * P&#257;li
     * ({@link LanguageCode#pi pi}).
     */
    pli("P\u0101li") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.pi;
        }
    },

    /**
     * Polish
     * ({@link LanguageCode#pl pl}).
     */
    pol("Polish") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.pl;
        }
    },

    /**
     * Pohnpeian
     */
    pon("Pohnpeian"),

    /**
     * Portuguese
     * ({@link LanguageCode#pt pt}).
     */
    por("Portuguese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.pt;
        }
    },

    /**
     * Malayo-Polynesian languages
     */
    poz("Malayo-Polynesian languages"),

    /**
     * Eastern Malayo-Polynesian languages
     */
    pqe("Eastern Malayo-Polynesian languages"),

    /**
     * Western Malayo-Polynesian languages
     */
    pqw("Western Malayo-Polynesian languages"),

    /**
     * Prakrit languages
     */
    pra("Prakrit languages"),

    pro("Old Proven\u00E7al"),

    /**
     * Pashto
     * ({@link LanguageCode#ps ps}).
     */
    pus("Pushto") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ps;
        }
    },

    /**
     * Quechua
     * ({@link LanguageCode#qu qu}).
     */
    que("Quechua") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.qu;
        }
    },

    /**
     * Quechuan (family)
     */
    qwe("Quechuan (family)"),

    /**
     * Rajasthani
     */
    raj("Rajasthani"),

    /**
     * Rapanui
     */
    rap("Rapanui"),

    /**
     * Rarotongan
     */
    rar("Rarotongan"),

    /**
     * Romance languages
     */
    roa("Romance languages"),

    /**
     * Romansh
     * ({@link LanguageCode#rm rm})
     */
    roh("Romansh") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.rm;
        }
    },

    /**
     * Romany
     */
    rom("Romany"),

    /**
     * Romanian
     * ({@link LanguageCode#ro ro}) for terminology applications.
     *
     * @see #rum
     */
    ron("Romanian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ro;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return rum;
        }
    },

    /**
     * Romanian
     * ({@link LanguageCode#ro ro}) for bibliographic applications.
     *
     * @see #ron
     */
    rum("Romansh") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ro;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return ron;
        }
    },

    /**
     * Kirundi
     * ({@link LanguageCode#rn rn}).
     */
    run("Kirundi") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.rn;
        }
    },

    /**
     * Macedo-Romanian
     */
    rup("Macedo-Romanian"),

    /**
     * Russian
     * ({@link LanguageCode#ru ru}).
     */
    rus("Russian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ru;
        }
    },

    /**
     * Sango
     */
    sad("Sango"),

    /**
     * Sango
     * ({@link LanguageCode#sg sg}).
     */
    sag("Sango") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sg;
        }
    },

    /**
     * Yakut
     */
    sah("Yakut"),

    /**
     * South American Indian languages
     */
    sai("South American Indian languages"),

    /**
     * Salishan languages
     */
    sal("Salishan languages"),

    /**
     * Samaritan Aramaic
     */
    sam("Samaritan Aramaic"),

    /**
     * Sanskrit
     * ({@link LanguageCode#sa sa}).
     */
    san("Sanskrit") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sa;
        }
    },

    /**
     * Sasak
     */
    sas("Sasak"),

    /**
     * Santali
     */
    sat("Santali"),

    /**
     * Sicilian
     */
    scn("Sicilian"),

    /**
     * Scots
     */
    sco("Scots"),

    /**
     * Eastern Sudanic languages
     */
    sdv("Eastern Sudanic languages"),

    /**
     * Selkup
     */
    sel("Selkup"),

    /**
     * Semitic languages
     */
    sem("Semitic languages"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Old_Irish_language"
     * >Old Irish</a> (to 900)
     */
    sga("Old Irish"),

    /**
     * Sign languages
     */
    sgn("Sign languages"),

    /**
     * Shan
     */
    shn("Shan"),

    /**
     * Sidamo
     */
    sid("Sidamo"),

    /**
     * Sinhala
     * ({@link LanguageCode#si si}).
     */
    sin("Sinhala") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.si;
        }
    },

    /**
     * Siouan languages
     */
    sio("Siouan languages"),

    /**
     * Sino-Tibetan languages
     */
    sit("Sino-Tibetan languages"),

    /**
     * Slavic languages
     */
    sla("Slavic languages"),

    /**
     * Slovak
     * ({@link LanguageCode#sk sk}) for terminology aplications.
     *
     * @see #slo
     */
    slk("Slovak") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sk;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return slo;
        }
    },

    /**
     * Slovak
     * ({@link LanguageCode#sk sk}) for bibliographic aplications.
     *
     * @see #slk
     */
    slo("Slovak") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sk;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return slk;
        }
    },

    /**
     * Slovene
     * ({@link LanguageCode#sl sl}).
     */
    slv("Slovene") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sl;
        }
    },

    /**
     * Southern Sami
     */
    sma("Southern Sami"),

    /**
     * Northern Sami
     * ({@link LanguageCode#se se}).
     */
    sme("Northern Sami") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.se;
        }
    },

    /**
     * Sami languages
     */
    smi("Sami languages"),

    /**
     * Lule Sami
     */
    smj("Lule Sami"),

    /**
     * Inari Sami
     */
    smn("Inari Sami"),

    /**
     * Samoan
     * ({@link LanguageCode#sm sm}).
     */
    smo("Samoan") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sm;
        }
    },

    /**
     * Skolt Sami
     */
    sms("Skolt Sami"),

    /**
     * Shona
     * ({@link LanguageCode#sn sn}).
     */
    sna("Shona") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sn;
        }
    },

    /**
     * Sindhi
     * ({@link LanguageCode#sd sd}).
     */
    snd("Sindhi") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sd;
        }
    },

    /**
     * Soninke
     */
    snk("Soninke"),

    /**
     * Sogdian
     */
    sog("Sogdian"),

    /**
     * Somali
     * ({@link LanguageCode#so so}).
     */
    som("Somali") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.so;
        }
    },

    /**
     * Songhai languages
     */
    son("Songhai languages"),

    /**
     * Southern Sotho
     * ({@link LanguageCode#st st}).
     */
    sot("Southern Sotho") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.st;
        }
    },

    /**
     * Spanish
     * ({@link LanguageCode#es es}).
     */
    spa("Spanish") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.es;
        }
    },

    /**
     * Albanian
     * ({@link LanguageCode#sq sq}) for terminology applications.
     *
     * @see #alb
     */
    sqi("Albanian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sq;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return alb;
        }
    },

    /**
     * Albanian languages
     */
    sqj("Albanian languages"),

    /**
     * Sardinian
     * ({@link LanguageCode#sc sc}).
     */
    srd("Sardinian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sc;
        }
    },

    /**
     * Sranan Tongo
     */
    srn("Sranan Tongo"),

    /**
     * Serbian
     * ({@link LanguageCode#sr sr}).
     */
    srp("Serbian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sr;
        }
    },

    /**
     * Serer
     */
    srr("Serer"),

    /**
     * Nilo-Saharan languages
     */
    ssa("Nilo-Saharan languages"),

    /**
     * Swati
     * ({@link LanguageCode#ss ss}).
     */
    ssw("Swati") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ss;
        }
    },

    /**
     * Sukuma
     */
    suk("Sukuma"),

    /**
     * Sundanese
     * ({@link LanguageCode#su su}).
     */
    sun("Sundanese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.su;
        }
    },

    /**
     * Susu
     */
    sus("Susu"),

    /**
     * Sumerian
     */
    sux("Sumerian"),

    /**
     * <a href="http://en.wikipedia.org/wiki/Swahili_language"
     * >Swahili</a> (macrolanguage)
     * ({@link LanguageCode#sw sw}).
     */
    swa("Swahili") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sw;
        }
    },

    /**
     * Swedish
     * ({@link LanguageCode#sv sv}).
     */
    swe("Swedish") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.sv;
        }
    },

    /**
     * Classical Syriac
     */
    syc("Classical Syriac"),

    /**
     * Samoyedic languages
     */
    syd("Samoyedic languages"),

    /**
     * Syriac
     */
    syr("Syriac"),

    /**
     * Tahitian
     * ({@link LanguageCode#ty ty}).
     */
    tah("Tahitian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ty;
        }
    },

    /**
     * Tai languages
     */
    tai("Tai languages"),

    /**
     * Tamil
     * ({@link LanguageCode#ta ta}).
     */
    tam("Tamil") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ta;
        }
    },

    /**
     * Tatar
     * ({@link LanguageCode#tt tt}).
     */
    tat("Tatar") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tt;
        }
    },

    /**
     * Tibeto-Burman languages
     */
    tbq("Tibeto-Burman languages"),

    /**
     * Telugu
     * ({@link LanguageCode#te te}).
     */
    tel("Telugu") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.te;
        }
    },

    /**
     * Timne
     */
    tem("Timne"),

    /**
     * Tereno
     */
    ter("Tereno"),

    /**
     * Tetum
     */
    tet("Tetum"),

    /**
     * Tajik
     * ({@link LanguageCode#tg tg}).
     */
    tgk("Tajik") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tg;
        }
    },

    /**
     * Tagalog
     * ({@link LanguageCode#tl tl}).
     */
    tgl("Tagalog") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tl;
        }
    },

    /**
     * Thai
     * ({@link LanguageCode#th th}).
     */
    tha("Thai") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.th;
        }
    },

    /**
     * Tibetan
     * ({@link LanguageCode#bo bo}) for terminology applications.
     *
     * @see #bod
     */
    tib("Tibetan") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.bo;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return bod;
        }
    },

    /**
     * Tigre
     */
    tig("Tigre"),

    /**
     * Tigrinya
     * ({@link LanguageCode#ti ti}).
     */
    tir("Tigrinya") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ti;
        }
    },

    /**
     * Tiv
     */
    tiv("Tiv"),

    /**
     * Tokelau
     */
    tkl("Tokelau"),

    /**
     * Klingon
     */
    tlh("Klingon"),

    /**
     * Tlingit
     */
    tli("Tlingit"),

    /**
     * Tamashek
     */
    tmh("Tamashek"),

    /**
     * Tonga (Nyasa)
     */
    tog("Tonga (Nyasa)"),

    /**
     * Tonga (Tonga Islands)
     * ({@link LanguageCode#to to}).
     */
    ton("Tonga (Tonga Islands)") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.to;
        }
    },

    /**
     * Tok Pisin
     */
    tpi("Tok Pisin"),

    /**
     * Turkic languages
     */
    trk("Turkic languages"),

    /**
     * Tsimshian
     */
    tsi("Tsimshian"),

    /**
     * Tswana
     * ({@link LanguageCode#tn tn}).
     */
    tsn("Tswana") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tn;
        }
    },

    /**
     * Tsonga
     * ({@link LanguageCode#ts ts}).
     */
    tso("Tsonga") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ts;
        }
    },

    /**
     * Turkmen
     * ({@link LanguageCode#tk tk}).
     */
    tuk("Turkmen") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tk;
        }
    },

    /**
     * Tumbuka
     */
    tum("Tumbuka"),

    /**
     * Tupi languages
     */
    tup("Tupi languages"),

    /**
     * Turkish
     * ({@link LanguageCode#tr tr}).
     */
    tur("Turkish") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tr;
        }
    },

    /**
     * Altaic languages
     */
    tut("Altaic languages"),

    /**
     * Tungus languages
     */
    tuw("Tungus languages"),

    /**
     * Tuvalu
     */
    tvl("Tuvalu"),

    /**
     * Twi
     * ({@link LanguageCode#tw tw}).
     */
    twi("Twi") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.tw;
        }
    },

    /**
     * Tuvinian
     */
    tyv("Tuvinian"),

    /**
     * Udmurt
     */
    udm("Udmurt"),

    /**
     * Ugaritic
     */
    uga("Ugaritic"),

    /**
     * Uighur
     * ({@link LanguageCode#ug ug}).
     */
    uig("Uighur") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ug;
        }
    },

    /**
     * Ukrainian
     * ({@link LanguageCode#uk uk}).
     */
    ukr("Ukrainian") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.uk;
        }
    },

    /**
     * Umbundu
     */
    umb("Umbundu"),

    /**
     * Undetermined.
     */
    und("Undetermined"),

    /**
     * Urdu
     * ({@link LanguageCode#ur ur}).
     */
    urd("Urdu") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ur;
        }
    },

    /**
     * Uralic languages
     */
    urj("Uralic languages"),

    /**
     * Uzbek
     * ({@link LanguageCode#uz uz}).
     */
    uzb("Uzbek") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.uz;
        }
    },

    /**
     * Vai
     */
    vai("Vai"),

    /**
     * Venda
     * ({@link LanguageCode#ve ve}).
     */
    ven("Venda") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.ve;
        }
    },

    /**
     * Vietnamese
     * ({@link LanguageCode#vi vi}).
     */
    vie("Vietnamese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.vi;
        }
    },

    /**
     * Volap&uuml;k
     * ({@link LanguageCode#vo vo}).
     */
    vol("Volap\u00FCk") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.vo;
        }
    },

    /**
     * Votic
     */
    vot("Votic"),

    /**
     * Wakashan languages
     */
    wak("Wakashan languages"),

    /**
     * Wolaytta
     */
    wal("Wolaytta"),

    /**
     * Waray (Philippines)
     */
    war("Waray (Philippines)"),

    /**
     * Washo
     */
    was("Washo"),

    /**
     * Welsh
     * ({@link LanguageCode#cy cy}) for bibliographic applications.
     *
     * @see #cym
     */
    wel("Welsh") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.cy;
        }


        @Override
        public Usage getUsage() {
            return Usage.BIBLIOGRAPHY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return cym;
        }
    },

    /**
     * Sorbian languages
     */
    wen("Sorbian languages"),

    /**
     * Walloon
     * ({@link LanguageCode#wa wa}).
     */
    wln("Walloon") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.wa;
        }
    },

    /**
     * Wolof
     * ({@link LanguageCode#wo wo}).
     */
    wol("Wolof") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.wo;
        }
    },

    /**
     * Kalmyk
     */
    xal("Kalmyk"),

    /**
     * Mongolian languages
     */
    xgn("Mongolian languages"),

    /**
     * Xhosa
     * ({@link LanguageCode#xh xh}).
     */
    xho("Xhosa") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.xh;
        }
    },

    /**
     * Na-Dene languages
     */
    xnd("Na-Dene languages"),

    /**
     * Yao
     */
    yao("Yao"),

    /**
     * Yapese
     */
    yap("Yapese"),

    /**
     * Yiddish
     * ({@link LanguageCode#yi yi}).
     */
    yid("Yiddish") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.yi;
        }
    },

    /**
     * Yoruba
     * ({@link LanguageCode#yo yo}).
     */
    yor("Yoruba") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.yo;
        }
    },

    /**
     * Yupik languages
     */
    ypk("Yupik languages"),

    /**
     * Zapotec
     */
    zap("Zapotec"),

    /**
     * Blissymbols
     */
    zbl("Blissymbols"),

    /**
     * Zenaga
     */
    zen("Zenaga"),

    /**
     * Zhuang
     * ({@link LanguageCode#za za}).
     */
    zha("Zhuang") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.za;
        }
    },

    /**
     * Chinese
     * ({@link LanguageCode#zh zh}) for terminology applications.
     *
     * @see #chi
     */
    zho("Chinese") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.zh;
        }


        @Override
        public Usage getUsage() {
            return Usage.TERMINOLOGY;
        }


        @Override
        public LanguageAlpha3Code getSynonym() {
            return chi;
        }
    },

    /**
     * Chinese (family)
     */
    zhx("Chinese (family)"),

    /**
     * East Slavic languages
     */
    zle("East Slavic languages"),

    /**
     * South Slavic languages
     */
    zls("South Slavic languages"),

    /**
     * West Slavic languages
     */
    zlw("West Slavic languages"),

    /**
     * Zande languages
     */
    znd("Zande languages"),

    /**
     * Zulu
     * ({@link LanguageCode#zu zu}).
     */
    zul("Zulu") {
        @Override
        public LanguageCode getAlpha2() {
            return LanguageCode.zu;
        }
    },

    /**
     * Zuni
     */
    zun("Zuni"),

    /**
     * No linguistic content.
     */
    zxx("No linguistic content"),

    /**
     * Zaza
     */
    zza("Zaza");


    /**
     * Language name.
     */
    private final String name;


    LanguageAlpha3Code(String name) {
        this.name = name;
    }

    /**
     * Get a {@code LanguageAlpha3Code} that corresponds to a given
     * ISO 639-1 code
     * (2-letter lowercase code) or
     * ISO 639-2 code
     * (3-letter lowercase code).
     *
     * <p>
     * This method calls {@link #getByCode(String, boolean) getByCode}{@code (code, true)}.
     * Note that the behavior has changed since the version 1.13. In the older versions,
     * this method was an alias of {@code getByCode(code, false)}.
     * </p>
     *
     * @param code An ISO 639-1
     *             code (2-letter lowercase code) or an
     *             ISO 639-2
     *             code (3-letter lowercase code). Or "undefined".
     *             Note that if the given code is one of legacy language codes
     *             ("iw", "ji" and "in"), it is treated as its official counterpart
     *             ("he", "yi" and "id"), respectively. For example, if "in" is
     *             given, this method returns {@link #ind LanguageAlpha3Code.ind}.
     * @return A {@code LanguageAlpha3Code} instance, or {@code null} if not found.
     * If ISO 639-1
     * code (2-letter code) is given and the language has two
     * ISO 639-2
     * codes, ISO 639/T code ("terminological" code) is returned.
     */
    public static LanguageAlpha3Code getByCode(String code) {
        return getByCode(code, true);
    }

    /**
     * Get a {@code LanguageAlpha3Code} that corresponds to a given
     * ISO 639-1 code
     * (2-letter lowercase code) or
     * ISO 639-2 code
     * (3-letter lowercase code).
     *
     * <p>
     * This method calls {@link #getByCode(String, boolean) getByCode}{@code (code, false)}.
     * </p>
     *
     * @param code An ISO 639-1
     *             code (2-letter lowercase code) or an
     *             ISO 639-2
     *             code (3-letter lowercase code). Or "undefined" (case insensitive).
     *             Note that if the given code is one of legacy language codes
     *             ("iw", "ji" and "in"), it is treated as its official counterpart
     *             ("he", "yi" and "id"), respectively. For example, if "in" is
     *             given, this method returns {@link #ind LanguageAlpha3Code.ind}.
     * @return A {@code LanguageAlpha3Code} instance, or {@code null} if not found.
     * If ISO 639-1
     * code (2-letter code) is given and the language has two
     * ISO 639-2
     * codes, ISO 639/T code ("terminological" code) is returned.
     */
    public static LanguageAlpha3Code getByCodeIgnoreCase(String code) {
        return getByCode(code, false);
    }

    /**
     * Get a {@code LanguageAlpha3Code} that corresponds to a given
     * ISO 639-1 code
     * (2-letter lowercase code) or
     * ISO 639-2 code
     * (3-letter lowercase code).
     *
     * @param code          An ISO 639-1
     *                      code (2-letter lowercase code) or an
     *                      ISO 639-2
     *                      code (3-letter lowercase code). Or "undefined" (its case
     *                      sensitivity depends on the value of {@code caseSensitive}).
     *                      Note that if the given code is one of legacy language codes
     *                      ("iw", "ji" and "in"), it is treated as its official counterpart
     *                      ("he", "yi" and "id"), respectively. For example, if "in" is
     *                      given, this method returns {@link #ind LanguageAlpha3Code.ind}.
     * @param caseSensitive If {@code true}, the given code should consist of lowercase letters only.
     *                      If {@code false}, this method internally canonicalizes the given code by
     *                      {@link String#toLowerCase()} and then performs search. For
     *                      example, {@code getByCode("JPN", true)} returns {@code null}, but on the
     *                      other hand, {@code getByCode("JPN", false)} returns {@link #jpn
     *                      LanguageAlpha3Code.jpn}.
     *                      <p>
     *                      As an exceptional case, both {@code getByCode("New", true)} and
     *                      {@code getByCode("new", true)} return {@link #New} (Newari).
     * @return A {@code LanguageAlpha3Code} instance, or {@code null} if not found.
     * If ISO 639-1
     * code (2-letter code) is given and the language has two
     * ISO 639-2
     * codes, ISO 639/T code ("terminological" code) is returned.
     */
    public static LanguageAlpha3Code getByCode(String code, boolean caseSensitive) {
        code = canonicalize(code, caseSensitive);

        if (code == null) {
            return null;
        }

        switch (code.length()) {
            case 2:
                break;

            case 3:
            case 9:
                return getByEnumName(code);

            default:
                return null;
        }

        code = LanguageCode.canonicalize(code, caseSensitive);
        LanguageCode alpha2 = LanguageCode.getByEnumName(code);

        if (alpha2 == null) {
            return null;
        }

        return alpha2.getAlpha3();
    }

    static LanguageAlpha3Code getByEnumName(String name) {
        try {
            return Enum.valueOf(LanguageAlpha3Code.class, name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static String canonicalize(String code, boolean caseSensitive) {
        if (code == null || code.length() == 0) {
            return null;
        }

        if (!caseSensitive) {
            code = code.toLowerCase();
        }

        // A special case for Newari.
        if (code.equals("new")) {
            code = "New";
        }

        return code;
    }

    /**
     * Get a list of {@code LanguageAlpha3Code} by a name regular expression.
     *
     * <p>
     * This method is almost equivalent to {@link #findByName(Pattern)
     * findByName}{@code (Pattern.compile(regex))}.
     * </p>
     *
     * @param regex Regular expression for names.
     * @return List of {@code LanguageAlpha3Code}. If nothing has matched,
     * an empty list is returned.
     * @throws IllegalArgumentException               {@code regex} is {@code null}.
     * @throws java.util.regex.PatternSyntaxException {@code regex} failed to be compiled.
     */
    public static List<LanguageAlpha3Code> findByName(String regex) {
        if (regex == null) {
            throw new IllegalArgumentException("regex is null.");
        }

        // Compile the regular expression. This may throw
        // java.util.regex.PatternSyntaxException.
        Pattern pattern = Pattern.compile(regex);

        return findByName(pattern);
    }

    /**
     * Get a list of {@code LanguageAlpha3Code} by a name pattern.
     *
     * <p>
     * For example, the list obtained by the code snippet below:
     * </p>
     *
     * <pre style="background-color: #EEEEEE; margin-left: 2em; margin-right: 2em; border: 1px solid black; padding: 0.5em;">
     * Pattern pattern = Pattern.compile(<span style="color: darkred;">"Old.*"</span>);
     * List&lt;LanguageAlpha3Code&gt; list = LanguageAlpha3Code.findByName(pattern);</pre>
     *
     * <p>
     * contains 7 {@code LanguageAlpha3Code}s as listed below.
     * </p>
     *
     * <ol>
     * <li>{@link #ang} : Old English
     * <li>{@link #fro} : Old French
     * <li>{@link #goh} : Old High German
     * <li>{@link #non} : Old Norse
     * <li>{@link #peo} : Old Persian
     * <li>{@link #pro} : Old Proven&ccedil;al
     * <li>{@link #sga} : Old Irish
     * </ol>
     *
     * @param pattern Pattern to match names.
     * @return List of {@code LanguageAlpha3Code}. If nothing has matched,
     * an empty list is returned.
     * @throws IllegalArgumentException {@code pattern} is {@code null}.
     */
    public static List<LanguageAlpha3Code> findByName(Pattern pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null.");
        }

        List<LanguageAlpha3Code> list = new ArrayList<>();

        for (LanguageAlpha3Code entry : values()) {
            // If the name matches the given pattern.
            if (pattern.matcher(entry.getName()).matches()) {
                list.add(entry);
            }
        }

        return list;
    }

    /**
     * Get the language name.
     *
     * @return The language name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get ISO 639-1
     * language code.
     *
     * @return ISO 639-1
     * language code. This method may return {@code null}.
     */
    public LanguageCode getAlpha2() {
        return null;
    }

    /**
     * Get the usage of this language code.
     *
     * <p>
     * Most language codes return {@link Usage#COMMON Usage.COMMON}.
     * </p>
     *
     * @return The usage of this language code.
     */
    public Usage getUsage() {
        return Usage.COMMON;
    }

    /**
     * Get the synonym of this code.
     *
     * <p>
     * In most cases, this method simply returns <code>this</code> object. On
     * the other hand, language codes that have two alpha-3 codes, namely, ISO
     * 639-2/T code ("terminological" code) and ISO 639-2/B code
     * ("bibliographic" code), return their counterparts. For example,
     * {@link #deu LanguageAlpha3Code.deu}{@code .getSynonym()} returns
     * {@link #ger LanguageAlpha3Code.ger}.
     * </p>
     *
     * @return ISO 639-2
     * language code.
     */
    public LanguageAlpha3Code getSynonym() {
        return this;
    }

    /**
     * Get the bibliographic code of this language.
     *
     * <p>
     * Most languages have just one <a
     * href="http://en.wikipedia.org/wiki/ISO_639-2">ISO 639-2</a> code and they
     * simply return <code>this</code> object. Only ISO 639-2/T codes
     * ("terminological" codes) return counterpart objects. For example,
     * {@link LanguageAlpha3Code#fra LanguageAlpha3Code.fra}{@code .getAlpha3B()}
     * returns {@link LanguageAlpha3Code#fre LanguageAlpha3Code.fre}.
     * </p>
     *
     * @return The bibliographic code of this language.
     */
    public LanguageAlpha3Code getAlpha3B() {
        if (getUsage() == Usage.BIBLIOGRAPHY) {
            return this;
        } else {
            return getSynonym();
        }
    }

    /**
     * Get the terminological code of this language.
     *
     * <p>
     * Most languages have just one <a
     * href="http://en.wikipedia.org/wiki/ISO_639-2">ISO 639-2</a> code and they
     * simply return <code>this</code> object. Only ISO 639-2/B codes
     * ("bibliographic" codes) return counterpart objects. For example,
     * {@link LanguageAlpha3Code#fre LanguageAlpha3Code.fre}{@code .getAlpha3T()}
     * returns {@link LanguageAlpha3Code#fra LanguageAlpha3Code.fra}.
     *
     * </p>
     *
     * @return The terminological code of this language.
     */
    public LanguageAlpha3Code getAlpha3T() {
        if (getUsage() == Usage.TERMINOLOGY) {
            return this;
        } else {
            return getSynonym();
        }
    }


    /**
     * The usage of this language code.
     */
    public enum Usage {
        /**
         * Code for terminology applications (ISO 639-2/T).
         */
        TERMINOLOGY,

        /**
         * Code for bibliographic applications (ISO 639-2/B).
         */
        BIBLIOGRAPHY,

        /**
         * For all applications including both terminology and
         * bibliographic applications.
         */
        COMMON
    }
}
