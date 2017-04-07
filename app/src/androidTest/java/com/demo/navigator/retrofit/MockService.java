package com.demo.navigator.retrofit;

import com.demo.navigator.ds.model.NavigationEntries;
import com.google.gson.Gson;

import io.reactivex.Observable;
import retrofit2.mock.BehaviorDelegate;

/**
 * To improve performance when testing we use local data (mock data) to simplify testing.
 */
public final class MockService implements Service {
	private final BehaviorDelegate<Service> mDelegate;
	private static final String FEEDS = "{\n" + "  \"navigationEntries\": [\n" + "    {\n" + "      \"type\": \"section\",\n" + "      \"label\": \"Sortiment\",\n" + "      \"children\": [\n" + "   " +
			"     {\n" + "          \"type\": \"node\",\n" + "          \"label\": \"Alter\",\n" + "          \"children\": [\n" + "            {\n" + "              \"type\": \"node\",\n" + "      " +
			"        \"label\": \"Baby & Kleinkind\",\n" + "              \"children\": [\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"0-6" +
			" Monate\",\n" + "                  \"url\": \"http://www.mytoys.de/0-6-months/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "     " +
			"             \"label\": \"7-12 Monate\",\n" + "                  \"url\": \"http://www.mytoys.de/7-12-months/\"\n" + "                },\n" + "                {\n" + "                  " +
			"\"type\": \"link\",\n" + "                  \"label\": \"13-24 Monate\",\n" + "                  \"url\": \"http://www.mytoys.de/13-24-months/\"\n" + "                },\n" + "\n" +
			"\t\t\t\t    {\n" + "      \"type\": \"section\",\n" + "      \"label\": \"Service\",\n" + "      \"children\": [\n" + "        {\n" + "          \"type\": \"link\",\n" + "          " +
			"\"label\": \"Hilfe\",\n" + "          \"url\": \"http://www.mytoys.de/c/faq.html\"\n" + "        },\n" + "        {\n" + "          \"type\": \"link\",\n" + "          \"label\": \"Mein" +
			" Konto\",\n" + "          \"url\": \"https://checkout.mytoys.de/checkout/serviceOverview\"\n" + "        },\n" + "        {\n" + "          \"type\": \"link\",\n" + "          " +
			"\"label\": \"Kontakt\",\n" + "          \"url\": \"http://www.mytoys.de/c/kontakt.html\"\n" + "        },\n" + "        {\n" + "          \"type\": \"link\",\n" + "          \"label\": " +
			"\"Unsere Filialen\",\n" + "          \"url\": \"http://www.mytoys.de/c/filialen.html\"\n" + "        },\n" + "        {\n" + "          \"type\": \"external-link\",\n" + "          " +
			"\"label\": \"myToys Blog\",\n" + "          \"url\": \"http://www.mytoys.de/my-blog/\"\n" + "        },\n" + "        {\n" + "          \"type\": \"external-link\",\n" + "          " +
			"\"label\": \"myToys bei Facebook\",\n" + "          \"url\": \"https://www.facebook.com/myToys\"\n" + "        },\n" + "        {\n" + "          \"type\": \"external-link\",\n" + "    " +
			"      \"label\": \"Jobs bei myToys\",\n" + "          \"url\": \"https://mytoysgroup.jobs/\"\n" + "        }\n" + "      ]\n" + "    }\n" + "              ]\n" + "            },\n" + " " +
			"           {\n" + "              \"type\": \"node\",\n" + "              \"label\": \"Kindergarten\",\n" + "              \"children\": [\n" + "                {\n" + "                 " +
			" \"type\": \"link\",\n" + "                  \"label\": \"2-3 Jahre\",\n" + "                  \"url\": \"http://www.mytoys.de/24-47-months/\"\n" + "                },\n" + "           " +
			"     {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"4-5 Jahre\",\n" + "                  \"url\": \"http://www.mytoys.de/48-71-months/\"\n" + "      " +
			"          }\n" + "              ]\n" + "            },\n" + "            {\n" + "              \"type\": \"node\",\n" + "              \"label\": \"Grundschule\",\n" + "              " +
			"\"children\": [\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"6-7 Jahre\",\n" + "                  \"url\": \"http://www" +
			".mytoys.de/72-95-months/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"8-9 Jahre\",\n" + "         " +
			"         \"url\": \"http://www.mytoys.de/96-119-months/\"\n" + "                }\n" + "              ]\n" + "            },\n" + "            {\n" + "              \"type\": \"node\"," +
			"\n" + "              \"label\": \"Teenager\",\n" + "              \"children\": [\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": " +
			"\"10-12 Jahre\",\n" + "                  \"url\": \"http://www.mytoys.de/120-155-months/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\"," +
			"\n" + "                  \"label\": \"Über 12 Jahre\",\n" + "                  \"url\": \"http://www.mytoys.de/over-156-months/\"\n" + "                }\n" + "              ]\n" + "   " +
			"         }\n" + "          ]\n" + "        },\n" + "        {\n" + "          \"type\": \"node\",\n" + "          \"label\": \"Kategorien\",\n" + "          \"children\": [\n" + "      " +
			"      {\n" + "              \"type\": \"node\",\n" + "              \"label\": \"Baby & Schwangerschaft\",\n" + "              \"children\": [\n" + "                {\n" + "            " +
			"      \"type\": \"link\",\n" + "                  \"label\": \"Alles von Babymode\",\n" + "                  \"url\": \"http://www.mytoys.de/baby-schwangerschaft/babymode/\"\n" + "     " +
			"           },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Alles für Unterwegs\",\n" + "                  \"url\": " +
			"\"http://www.mytoys.de/baby-schwangerschaft/alles-fuer-unterwegs/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  " +
			"\"label\": \"Babyalben & Kreatives\",\n" + "                  \"url\": \"http://www.mytoys.de/baby-schwangerschaft/babyalben-kreatives/\"\n" + "                },\n" + "                " +
			"{\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Babyernährung\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/baby-schwangerschaft/babyernaehrung/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Babymöbel\"," +
			"\n" + "                  \"url\": \"http://www.mytoys.de/baby-schwangerschaft/babymoebel/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\"," +
			"\n" + "                  \"label\": \"Babypflege\",\n" + "                  \"url\": \"http://www.mytoys.de/baby-schwangerschaft/babypflege/\"\n" + "                },\n" + "           " +
			"     {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Babyschlaf\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/baby-schwangerschaft/babyschlaf/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Babyspielzeug\"," +
			"\n" + "                  \"url\": \"http://www.mytoys.de/baby-schwangerschaft/babyspielzeug/\"\n" + "                },\n" + "                {\n" + "                  \"type\": " +
			"\"link\",\n" + "                  \"label\": \"Kindersitze & Babyschalen\",\n" + "                  \"url\": \"http://www.mytoys.de/baby-schwangerschaft/kindersitze-babyschalen/\"\n" +
			"                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Kinderwagen & Zubehör\",\n" + "                  \"url\": " +
			"\"http://www.mytoys.de/baby-schwangerschaft/kinderwagen-zubehoer/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  " +
			"\"label\": \"Schnuller & Zubehör\",\n" + "                  \"url\": \"http://www.mytoys.de/baby-schwangerschaft/schnuller-zubehoer/\"\n" + "                },\n" + "                " +
			"{\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Sicherheit für's Baby\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/baby-schwangerschaft/sicherheit-fuers-baby/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": " +
			"\"Stillen & Pflege für die Mama\",\n" + "                  \"url\": \"http://www.mytoys.de/baby-schwangerschaft/stillen-pflege-fuer-die-mama/\"\n" + "                },\n" + "          " +
			"      {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Stillmode\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/baby-schwangerschaft/stillmode/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Umstandsmode\"," +
			"\n" + "                  \"url\": \"http://www.mytoys.de/baby-schwangerschaft/umstandsmode/\"\n" + "                }\n" + "              ]\n" + "            },\n" + "            {\n" +
			"              \"type\": \"node\",\n" + "              \"label\": \"Spielzeug & Spiele\",\n" + "              \"children\": [\n" + "                {\n" + "                  \"type\": " +
			"\"link\",\n" + "                  \"label\": \"Alles von Spielzeug & Spiele\",\n" + "                  \"url\": \"http://www.mytoys.de/spielzeug-spiele/\"\n" + "                },\n" +
			"                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Autos, Fahrzeuge & Flieger\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/spielzeug-spiele/autos-fahrzeuge-flieger/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Baby- &" +
			" Kleinkindspielzeug\",\n" + "                  \"url\": \"http://www.mytoys.de/spielzeug-spiele/baby-kleinkindspielzeug/\"\n" + "                },\n" + "                {\n" + "       " +
			"           \"type\": \"link\",\n" + "                  \"label\": \"Bauen & Konstruieren\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/spielzeug-spiele/bauen-konstruieren/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": " +
			"\"Experimentieren & Entdecken\",\n" + "                  \"url\": \"http://www.mytoys.de/spielzeug-spiele/experimentieren-entdecken/\"\n" + "                },\n" + "                " +
			"{\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Kinderfahrzeuge\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/spielzeug-spiele/kinderfahrzeuge/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Kinderparty\"," +
			"\n" + "                  \"url\": \"http://www.mytoys.de/spielzeug-spiele/kinderparty-zubehoer/\"\n" + "                },\n" + "                {\n" + "                  \"type\": " +
			"\"link\",\n" + "                  \"label\": \"Kostüme & Rollenspielzeug\",\n" + "                  \"url\": \"http://www.mytoys.de/spielzeug-spiele/kostueme-rollenspielzeug/\"\n" + "  " +
			"              },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Kuscheltiere & Teddys\",\n" + "                  \"url\": " +
			"\"http://www.mytoys.de/spielzeug-spiele/kuscheltiere-teddys/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  " +
			"\"label\": \"Küche & Kaufladen\",\n" + "                  \"url\": \"http://www.mytoys.de/spielzeug-spiele/kueche-kaufladen/\"\n" + "                },\n" + "                {\n" + "   " +
			"               \"type\": \"link\",\n" + "                  \"label\": \"Lernspielzeug\",\n" + "                  \"url\": \"http://www.mytoys.de/spielzeug-spiele/lernspielzeug/\"\n" + "" +
			"                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Musikinstrumente\",\n" + "                  \"url\": " +
			"\"http://www.mytoys.de/spielzeug-spiele/musikinstrumente/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  " +
			"\"label\": \"Puppen & Puppenzubehör\",\n" + "                  \"url\": \"http://www.mytoys.de/spielzeug-spiele/puppen-puppenzubehoer/\"\n" + "                },\n" + "                " +
			"{\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Puzzle\",\n" + "                  \"url\": \"http://www.mytoys.de/spielzeug-spiele/puzzle/\"\n" + "   " +
			"             },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Spiele\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/spielzeug-spiele/spiele/\"\n" + "                }\n" + "              ]\n" + "            },\n" + "            {\n" + "              \"type\": \"node\",\n" + "              " +
			"\"label\": \"Mode & Schuhe\",\n" + "              \"children\": [\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Alles von Mode" +
			" & Schuhe\",\n" + "                  \"url\": \"http://www.mytoys.de/mode-schuhe/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "  " +
			"                \"label\": \"Accessoires\",\n" + "                  \"url\": \"http://www.mytoys.de/mode-schuhe/accessoires/\"\n" + "                },\n" + "                {\n" + "   " +
			"               \"type\": \"link\",\n" + "                  \"label\": \"Bademode\",\n" + "                  \"url\": \"http://www.mytoys.de/mode-schuhe/bademode/\"\n" + "               " +
			" },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Hosen, Jeans & Leggings\",\n" + "                  \"url\": \"http://www" +
			".mytoys.de/mode-schuhe/hosen-jeans-leggings/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Jacken &" +
			" Mäntel\",\n" + "                  \"url\": \"http://www.mytoys.de/mode-schuhe/jacken-maentel/\"\n" + "                },\n" + "                {\n" + "                  \"type\": " +
			"\"link\",\n" + "                  \"label\": \"Kleider & Röcke\",\n" + "                  \"url\": \"http://www.mytoys.de/mode-schuhe/kleider-roecke/\"\n" + "                },\n" + "  " +
			"              {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Mützen, Hüte & Caps\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/mode-schuhe/muetzen-huete-caps/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Oberteile\",\n" +
			"                  \"url\": \"http://www.mytoys.de/mode-schuhe/oberteile/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "           " +
			"       \"label\": \"Regenbekleidung\",\n" + "                  \"url\": \"http://www.mytoys.de/mode-schuhe/regenbekleidung/\"\n" + "                },\n" + "                {\n" + "    " +
			"              \"type\": \"link\",\n" + "                  \"label\": \"Rucksäcke & Taschen\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/mode-schuhe/rucksaecke-taschen/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Schnee- & " +
			"Skibeleidung\",\n" + "                  \"url\": \"http://www.mytoys.de/mode-schuhe/schnee-skibekleidung/\"\n" + "                },\n" + "                {\n" + "                  " +
			"\"type\": \"link\",\n" + "                  \"label\": \"Kinderschuhe\",\n" + "                  \"url\": \"http://www.mytoys.de/mode-schuhe/schuhe/\"\n" + "                },\n" + "   " +
			"             {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Sportbekleidung\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/mode-schuhe/sportbekleidung/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Strümpfe & " +
			"Strumpfhosen\",\n" + "                  \"url\": \"http://www.mytoys.de/mode-schuhe/struempfe-strumpfhosen/\"\n" + "                },\n" + "                {\n" + "                  " +
			"\"type\": \"link\",\n" + "                  \"label\": \"Wäsche\",\n" + "                  \"url\": \"http://www.mytoys.de/mode-schuhe/waesche/\"\n" + "                }\n" + "         " +
			"     ]\n" + "            },\n" + "            {\n" + "              \"type\": \"node\",\n" + "              \"label\": \"Basteln & Malen\",\n" + "              \"children\": [\n" + "   " +
			"             {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Alles von Basteln & Malen\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/basteln-malen/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Bastel- & Kreativsets\",\n" + "   " +
			"               \"url\": \"http://www.mytoys.de/basteln-malen/bastel-kreativsets/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "   " +
			"               \"label\": \"Bastelbücher\",\n" + "                  \"url\": \"http://www.mytoys.de/basteln-malen/bastelbuecher/\"\n" + "                },\n" + "                {\n" +
			"                  \"type\": \"link\",\n" + "                  \"label\": \"Bastelmaterial & Zubehör\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/basteln-malen/bastelmaterial-zubehoer/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": " +
			"\"Papeterie\",\n" + "                  \"url\": \"http://www.mytoys.de/basteln-malen/papeterie/\"\n" + "                },\n" + "                {\n" + "                  \"type\": " +
			"\"link\",\n" + "                  \"label\": \"Stempel & Sticker\",\n" + "                  \"url\": \"http://www.mytoys.de/basteln-malen/stempel-sticker/\"\n" + "                },\n"
			+ "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Stifte & Farben\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/basteln-malen/stifte-farben/\"\n" + "                }\n" + "              ]\n" + "            },\n" + "            {\n" + "              \"type\": \"node\",\n" + "              " +
			"\"label\": \"Multimedia & Bücher\",\n" + "              \"children\": [\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Alles " +
			"von Multimedia & Bücher\",\n" + "                  \"url\": \"http://www.mytoys.de/multimedia-buecher/\"\n" + "                },\n" + "                {\n" + "                  " +
			"\"type\": \"link\",\n" + "                  \"label\": \"Bücher\",\n" + "                  \"url\": \"http://www.mytoys.de/multimedia-buecher/buecher/\"\n" + "                },\n" + " " +
			"               {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"CD-Player, MP3-Player & Anlagen\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/multimedia-buecher/cd-player-mp3-player-anlagen/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": " +
			"\"CDs & MCs\",\n" + "                  \"url\": \"http://www.mytoys.de/multimedia-buecher/cds-mcs/\"\n" + "                },\n" + "                {\n" + "                  \"type\": " +
			"\"link\",\n" + "                  \"label\": \"DVDs & BLU-RAYs\",\n" + "                  \"url\": \"http://www.mytoys.de/multimedia-buecher/dvds-blu-rays/\"\n" + "                },\n"
			+ "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Foto-& Videokameras\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/multimedia-buecher/foto-videokameras/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Konsolen & " +
			"PC Software\",\n" + "                  \"url\": \"http://www.mytoys.de/multimedia-buecher/konsolen-pc-zubehoer/\"\n" + "                },\n" + "                {\n" + "                " +
			"  \"type\": \"link\",\n" + "                  \"label\": \"Tablet PCs & Smartphones\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/multimedia-buecher/tablet-pcs-smartphones/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": " +
			"\"Videospiele (PC & Konsole)\",\n" + "                  \"url\": \"http://www.mytoys.de/multimedia-buecher/videospiele-pc-konsole/\"\n" + "                },\n" + "                {\n"
			+ "                  \"type\": \"link\",\n" + "                  \"label\": \"Wecker\",\n" + "                  \"url\": \"http://www.mytoys.de/multimedia-buecher/wecker/\"\n" + "       " +
			"         },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Zubehör\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/multimedia-buecher/zubehoer/\"\n" + "                }\n" + "              ]\n" + "            },\n" + "            {\n" + "              \"type\": \"node\",\n" + "              " +
			"\"label\": \"Schule & Lernen\",\n" + "              \"children\": [\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Alles von " +
			"Schule & Lernen\",\n" + "                  \"url\": \"http://www.mytoys.de/schule-lernen/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\"," +
			"\n" + "                  \"label\": \"Lernspiele & Lernhilfen\",\n" + "                  \"url\": \"http://www.mytoys.de/schule-lernen/lernspiele-lernhilfen/\"\n" + "                }," +
			"\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Schreibtische & -utensilien\",\n" + "                  \"url\": \"http://www" +
			".mytoys.de/schule-lernen/schreibtische-utensilien/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": " +
			"\"Schulbedarf\",\n" + "                  \"url\": \"http://www.mytoys.de/schule-lernen/schulbedarf/\"\n" + "                },\n" + "                {\n" + "                  \"type\": " +
			"\"link\",\n" + "                  \"label\": \"Schulbücher\",\n" + "                  \"url\": \"http://www.mytoys.de/schule-lernen/schulbuecher/\"\n" + "                },\n" + "      " +
			"          {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Schulranzen & Zubehör\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/schule-lernen/schulranzen-zubehoer/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Stifte & " +
			"Schreibgeräte\",\n" + "                  \"url\": \"http://www.mytoys.de/schule-lernen/stifte-schreibgeraete/\"\n" + "                }\n" + "              ]\n" + "            },\n" + "" +
			"            {\n" + "              \"type\": \"node\",\n" + "              \"label\": \"Sport & Garten\",\n" + "              \"children\": [\n" + "                {\n" + "              " +
			"    \"type\": \"link\",\n" + "                  \"label\": \"Alles von Sport & Garten\",\n" + "                  \"url\": \"http://www.mytoys.de/sport-garten/\"\n" + "                }," +
			"\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Camping & Outdoor\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/sport-garten/camping-outdoor/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Fahrräder & " +
			"Zubehör\",\n" + "                  \"url\": \"http://www.mytoys.de/sport-garten/fahrraeder-zubehoer/\"\n" + "                },\n" + "                {\n" + "                  \"type\":" +
			" \"link\",\n" + "                  \"label\": \"Gartenmöbel & Accessoires\",\n" + "                  \"url\": \"http://www.mytoys.de/sport-garten/gartenmoebel-accessoires/\"\n" + "     " +
			"           },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Gartenspielzeug\",\n" + "                  \"url\": \"http://www" +
			".mytoys.de/sport-garten/gartenspielzeug/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": " +
			"\"Kinderfahrzeuge\",\n" + "                  \"url\": \"http://www.mytoys.de/sport-garten/kinderfahrzeuge/\"\n" + "                },\n" + "                {\n" + "                  " +
			"\"type\": \"link\",\n" + "                  \"label\": \"Kletter- und Spielgeräte\",\n" + "                  \"url\": \"http://www.mytoys.de/sport-garten/kletter-und-spielgeraete/\"\n"
			+ "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Sand- & Wasserspaß\",\n" + "                  \"url\": " +
			"\"http://www.mytoys.de/sport-garten/sand-wasserspass/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": " +
			"\"Sportbekleidung & Sportschuhe\",\n" + "                  \"url\": \"http://www.mytoys.de/mode-schuhe/sportbekleidung/\"\n" + "                },\n" + "                {\n" + "        " +
			"          \"type\": \"link\",\n" + "                  \"label\": \"Sportzubehör & Sporttaschen\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/sport-garten/sportzubehoer-sporttaschen/\"\n" + "                }\n" + "              ]\n" + "            },\n" + "            {\n" + "              \"type\": \"node\",\n" + "     " +
			"         \"label\": \"Kinderzimmer & Wohnen\",\n" + "              \"children\": [\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\":" +
			" \"Alles von Kinderzimmer & Wohnen\",\n" + "                  \"url\": \"http://www.mytoys.de/kinderzimmer-wohnen/\"\n" + "                },\n" + "                {\n" + "             " +
			"     \"type\": \"link\",\n" + "                  \"label\": \"Badaccessoires\",\n" + "                  \"url\": \"http://www.mytoys.de/kinderzimmer-wohnen/badaccessoires/\"\n" + "     " +
			"           },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Betten & Zubehör\",\n" + "                  \"url\": \"http://www" +
			".mytoys.de/kinderzimmer-wohnen/betten-zubehoer/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": " +
			"\"Geschirr & Besteck\",\n" + "                  \"url\": \"http://www.mytoys.de/kinderzimmer-wohnen/geschirr-besteck/\\\"\"\n" + "                },\n" + "                {\n" + "      " +
			"            \"type\": \"link\",\n" + "                  \"label\": \"Haushaltshelfer\",\n" + "                  \"url\": \"http://www.mytoys.de/kinderzimmer-wohnen/haushaltshelfer/\"\n"
			+ "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Heimtextilien\",\n" + "                  \"url\": " +
			"\"http://www.mytoys.de/kinderzimmer-wohnen/heimtextilien/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  " +
			"\"label\": \"Kleinmöbel\",\n" + "                  \"url\": \"http://www.mytoys.de/kinderzimmer-wohnen/kleinmoebel/\"\n" + "                },\n" + "                {\n" + "            " +
			"      \"type\": \"link\",\n" + "                  \"label\": \"Komplettzimmer\",\n" + "                  \"url\": \"http://www.mytoys.de/kinderzimmer-wohnen/komplettzimmer/\"\n" + "    " +
			"            },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Leuchten\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/kinderzimmer-wohnen/leuchten/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Matratzen & " +
			"Federholzrahmen\",\n" + "                  \"url\": \"http://www.mytoys.de/kinderzimmer-wohnen/matratzen-federholzrahmen/\"\n" + "                },\n" + "                {\n" + "      " +
			"            \"type\": \"link\",\n" + "                  \"label\": \"Ordnen & Aufbewahren\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/kinderzimmer-wohnen/ordnen-aufbewahren/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Schränke " +
			"& Zubehör\",\n" + "                  \"url\": \"http://www.mytoys.de/kinderzimmer-wohnen/schraenke-zubehoer/\"\n" + "                },\n" + "                {\n" + "                  " +
			"\"type\": \"link\",\n" + "                  \"label\": \"Schulmöbel\",\n" + "                  \"url\": \"http://www.mytoys.de/kinderzimmer-wohnen/schulmoebel/\"\n" + "                " +
			"},\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Tische & Sitzmöbel\",\n" + "                  \"url\": \"http://www.mytoys" +
			".de/kinderzimmer-wohnen/sitzmoebel/\"\n" + "                },\n" + "                {\n" + "                  \"type\": \"link\",\n" + "                  \"label\": \"Wohndeko & " +
			"Accessoires\",\n" + "                  \"url\": \"http://www.mytoys.de/kinderzimmer-wohnen/wohndeko-accessoires/\"\n" + "                }\n" + "              ]\n" + "            }\n" +
			"          ]\n" + "        },\n" + "        {\n" + "          \"type\": \"node\",\n" + "          \"label\": \"Lieblingsstars\",\n" + "          \"children\": [\n" + "            {\n" +
			"              \"type\": \"link\",\n" + "              \"label\": \"Paw Patrol\",\n" + "              \"url\": \"http://www.mytoys.de/paw-patrol/\"\n" + "            },\n" + "           " +
			" {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Disney Cars\",\n" + "              \"url\": \"http://www.mytoys.de/disney-cars/\"\n" + "            },\n" + "" +
			"            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Dragons\",\n" + "              \"url\": \"http://www.mytoys.de/dragons/\"\n" + "            },\n"
			+ "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Eiskönigin\",\n" + "              \"url\": \"http://www.mytoys.de/disney-die-eiskoenigin/\"\n" +
			"            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Feuerwehrmann Sam\",\n" + "              \"url\": \"http://www.mytoys" +
			".de/feuerwehrmann-sam/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Hello Kitty\",\n" + "              \"url\": " +
			"\"http://www.mytoys.de/hello-kitty/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Marvel\",\n" + "              " +
			"\"url\": \"http://www.mytoys.de/marvel-avengers_marvel-heroes/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": " +
			"\"MiaMe\",\n" + "              \"url\": \"http://www.mytoys.de/mia-me/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": " +
			"\"Minnie Mouse\",\n" + "              \"url\": \"http://www.mytoys.de/disney-minnie-mouse/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "     " +
			"         \"label\": \"Minions\",\n" + "              \"url\": \"http://www.mytoys.de/minions/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "  " +
			"            \"label\": \"Monster High\",\n" + "              \"url\": \"http://www.mytoys.de/monster-high/\"\n" + "            },\n" + "            {\n" + "              \"type\": " +
			"\"link\",\n" + "              \"label\": \"Top Model\",\n" + "              \"url\": \"http://www.mytoys.de/topmodel/\"\n" + "            },\n" + "            {\n" + "              " +
			"\"type\": \"link\",\n" + "              \"label\": \"Disney Princess\",\n" + "              \"url\": \"http://www.mytoys.de/license-disney-princess/\"\n" + "            },\n" + "       " +
			"     {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Star Wars\",\n" + "              \"url\": \"http://www.mytoys.de/star-wars/\"\n" + "            },\n" + "" +
			"            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Violetta\",\n" + "              \"url\": \"http://www.mytoys.de/disney-violetta/\"\n" + "         " +
			"   },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"WinniePuuh\",\n" + "              \"url\": \"http://www.mytoys" +
			".de/disney-winnie-puuh/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Yakari\",\n" + "              \"url\": " +
			"\"http://www.mytoys.de/yakari/\"\n" + "            }\n" + "          ]\n" + "        },\n" + "        {\n" + "          \"type\": \"node\",\n" + "          \"label\": \"Top-Marken\",\n"
			+ "          \"children\": [\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"4YOU\",\n" + "              \"url\": \"http://www.mytoys" +
			".de/4you/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"adidas\",\n" + "              \"url\": \"http://www.mytoys" +
			".de/adidas_adidas-neo_adidas-performance/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Barbie\",\n" + "           " +
			"   \"url\": \"http://www.mytoys.de/license-barbie/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"MaxiCosi\",\n" + "" +
			"              \"url\": \"http://www.mytoys.de/maxi-cosi/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Nintendo\"," +
			"\n" + "              \"url\": \"http://www.mytoys.de/nintendo/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": " +
			"\"Playmobil\",\n" + "              \"url\": \"http://www.mytoys.de/playmobil-sub/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              " +
			"\"label\": \"Fisher-Price\",\n" + "              \"url\": \"http://www.mytoys.de/fisher-price/\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + " " +
			"             \"label\": \"Ravensburger\",\n" + "              \"url\": \"http://www.mytoys.de/ravensburger/\"\n" + "            },\n" + "            {\n" + "              \"type\": " +
			"\"link\",\n" + "              \"label\": \"vTech\",\n" + "              \"url\": \"http://www.mytoys.de/vtech/\"\n" + "            },\n" + "            {\n" + "              \"type\": " +
			"\"link\",\n" + "              \"label\": \"Stabilo\",\n" + "              \"url\": \"http://www.mytoys.de/stabilo/\"\n" + "            },\n" + "            {\n" + "              " +
			"\"type\": \"link\",\n" + "              \"label\": \"Stokke\",\n" + "              \"url\": \"http://www.mytoys.de/stokke/\"\n" + "            },\n" + "            {\n" + "             " +
			" \"type\": \"link\",\n" + "              \"label\": \"Lego\",\n" + "              \"url\": \"http://www.mytoys.de/lego-sub/\"\n" + "            },\n" + "            {\n" + "            " +
			"  \"type\": \"link\",\n" + "              \"label\": \"sOliver\",\n" + "              \"url\": \"http://www.mytoys.de/s-oliver/\"\n" + "            },\n" + "            {\n" + "        " +
			"      \"type\": \"link\",\n" + "              \"label\": \"Hudora\",\n" + "              \"url\": \"http://www.mytoys.de/hudora/\"\n" + "            },\n" + "            {\n" + "       " +
			"       \"type\": \"link\",\n" + "              \"label\": \"Jack Wolfskin\",\n" + "              \"url\": \"http://www.mytoys.de/jack-wolfskin/\"\n" + "            },\n" + "            " +
			"{\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Spiegelburg\",\n" + "              \"url\": \"http://www.mytoys.de/die-spiegelburg/\"\n" + "            },\n"
			+ "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Esprit\",\n" + "              \"url\": \"ttp://www.mytoys.de/esprit/\"\n" + "            }\n" +
			"          ]\n" + "        }\n" + "      ]\n" + "    },\n" + "    {\n" + "      \"type\": \"section\",\n" + "      \"label\": \"Schnäppchen\",\n" + "      \"children\": [\n" + "        " +
			"{\n" + "          \"type\": \"link\",\n" + "          \"label\": \"Alle Angebote im Überblick\",\n" + "          \"url\": \"http://www.mytoys.de/sale/\"\n" + "        },\n" + "        " +
			"{\n" + "          \"type\": \"link\",\n" + "          \"label\": \"Baby Schnäppchen\",\n" + "          \"url\": \"http://www.mytoys.de/baby-schwangerschaft/sale/\"\n" + "        },\n" +
			"        {\n" + "          \"type\": \"link\",\n" + "          \"label\": \"Spielzeug & Spiele Sale\",\n" + "          \"url\": \"http://www.mytoys.de/spielzeug-spiele/sale/\"\n" + "    " +
			"    },\n" + "        {\n" + "          \"type\": \"link\",\n" + "          \"label\": \"Mode & Schuhe Sale\",\n" + "          \"url\": \"http://www.mytoys.de/mode-schuhe/sale/\"\n" + " " +
			"       },\n" + "        {\n" + "          \"type\": \"link\",\n" + "          \"label\": \"Artikel aus der Werbung\",\n" + "          \"url\": \"http://www.mytoys.de/14e/\"\n" + "      " +
			"  },\n" + "        {\n" + "          \"type\": \"link\",\n" + "          \"label\": \"Knaller der Woche\",\n" + "          \"url\": \"http://www.mytoys.de/mkt/knaller-der-woche\"\n" + "" +
			"        }\n" + "      ]\n" + "    },\n" + "    {\n" + "      \"type\": \"section\",\n" + "      \"label\": \"Beratung & Inspiration\",\n" + "      \"children\": [\n" + "        {\n" + "" +
			"          \"type\": \"node\",\n" + "          \"label\": \"Geschenketipps\",\n" + "          \"children\": [\n" + "            {\n" + "              \"type\": \"link\",\n" + "          " +
			"    \"label\": \"Geschenketipps für Mädchen\",\n" + "              \"url\": \"http://www.mytoys.de/weiblich/geschenke/\"\n" + "            },\n" + "            {\n" + "              " +
			"\"type\": \"link\",\n" + "              \"label\": \"Geschenketipps für Jungen\",\n" + "              \"url\": \"http://www.mytoys.de/maennlich/geschenke/\"\n" + "            }\n" + "  " +
			"        ]\n" + "        },\n" + "        {\n" + "          \"type\": \"node\",\n" + "          \"label\": \"Ratgeber\",\n" + "          \"children\": [\n" + "            {\n" + "       " +
			"       \"type\": \"link\",\n" + "              \"label\": \"Alle Ratgeber\",\n" + "              \"url\": \"http://www.mytoys.de/c/ratgeber.html\"\n" + "            },\n" + "           " +
			" {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Baby\",\n" + "              \"url\": \"http://www.mytoys.de/c/babyratgeber.html\"\n" + "            },\n" + "" +
			"            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Mode\",\n" + "              \"url\": \"http://www.mytoys.de/c/ratgeber-kindermode.html\"\n" + "   " +
			"         },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"PC/Games\",\n" + "              \"url\": \"http://www.mytoys" +
			".de/c/einkaufsberatung-pc-und-games-einstiegsseite.html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Reisen mit " +
			"Kind\",\n" + "              \"url\": \"http://www.mytoys.de/c/ratgeber-reisen.html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "             " +
			" \"label\": \"Einschulung\",\n" + "              \"url\": \"http://www.mytoys.de/c/einkaufsberatung-schule-einstiegsseite.html\"\n" + "            },\n" + "            {\n" + "         " +
			"     \"type\": \"link\",\n" + "              \"label\": \"Sport\",\n" + "              \"url\": \"http://www.mytoys.de/c/einkaufsberatung-sport-einstiegsseite.html\"\n" + "            " +
			"}\n" + "          ]\n" + "        },\n" + "        {\n" + "          \"type\": \"node\",\n" + "          \"label\": \"Kinderparty\",\n" + "          \"children\": [\n" + "            " +
			"{\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Alle Kinderpartytipps\",\n" + "              \"url\": \"http://www.mytoys.de/c/kinderpartytipps.html\"\n" + " " +
			"           },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Baby\",\n" + "              \"url\": \"http://www.mytoys.de/c/babyparty" +
			".html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Cowboy- & Indianerparty\",\n" + "              \"url\": " +
			"\"http://www.mytoys.de/c/cowboy-und-indianer-party.html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Detektiv\"," +
			"\n" + "              \"url\": \"http://www.mytoys.de/c/detektivparty.html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              " +
			"\"label\": \"Dschungel- & Zoo\",\n" + "              \"url\": \"http://www.mytoys.de/c/dschungel-und-zoo-party.html\"\n" + "            },\n" + "            {\n" + "              " +
			"\"type\": \"link\",\n" + "              \"label\": \"Einschulung\",\n" + "              \"url\": \"http://www.mytoys.de/c/partyanlaesse-einschulung.html\"\n" + "            },\n" + "   " +
			"         {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Eiskönigin \",\n" + "              \"url\": \"http://www.mytoys.de/c/eiskoenigin-party.html\"\n" + " " +
			"           },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Fußballparty\",\n" + "              \"url\": \"http://www.mytoys" +
			".de/c/fussballparty.html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Halloween\",\n" + "              \"url\": " +
			"\"http://www.mytoys.de/c/halloweenparty.html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Geburtstag\",\n" + "    " +
			"          \"url\": \"http://www.mytoys.de/c/kindergeburtstagsparty.html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\":" +
			" \"Kinderolympiade\",\n" + "              \"url\": \"http://www.mytoys.de/c/kinderolympiade.html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" +
			"              \"label\": \"Kindertag\",\n" + "              \"url\": \"http://www.mytoys.de/c/partyanlaesse-kindertag.html\"\n" + "            },\n" + "            {\n" + "             " +
			" \"type\": \"link\",\n" + "              \"label\": \"Ostern\",\n" + "              \"url\": \"http://www.mytoys.de/c/osterfest.html\"\n" + "            },\n" + "            {\n" + "   " +
			"           \"type\": \"link\",\n" + "              \"label\": \"Piratenparty\",\n" + "              \"url\": \"http://www.mytoys.de/c/piratenparty.html\"\n" + "            },\n" + "    " +
			"        {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Prinzessinnenparty\",\n" + "              \"url\": \"http://www.mytoys.de/c/prinzessinnenparty" +
			".html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Ritterparty\",\n" + "              \"url\": \"http://www.mytoys" +
			".de/c/ritterparty.html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Gartenparty\",\n" + "              \"url\": " +
			"\"http://www.mytoys.de/c/gartenparty.html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Star Wars Party\",\n" + "  " +
			"            \"url\": \"http://www.mytoys.de/c/star-wars-party.html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": " +
			"\"Weihnachten\",\n" + "              \"url\": \"http://www.mytoys.de/c/weihnachtsparty.html\"\n" + "            }\n" + "          ]\n" + "        },\n" + "        {\n" + "          " +
			"\"type\": \"node\",\n" + "          \"label\": \"Lieblingsgeschenke\",\n" + "          \"children\": [\n" + "            {\n" + "              \"type\": \"link\",\n" + "              " +
			"\"label\": \"15 Lieblingsgeschenke zum Geburtstag\",\n" + "              \"url\": \"http://www.mytoys.de/mkt/lieblingsgeschenke-zum-geburtstag\"\n" + "            },\n" + "            " +
			"{\n" + "              \"type\": \"link\",\n" + "              \"label\": \"15 Lieblingsgeschenke für die Schultüte\",\n" + "              \"url\": \"http://www.mytoys" +
			".de/mkt/lieblingsgeschenke-fuer-die-schultuete\"\n" + "            }\n" + "          ]\n" + "        },\n" + "        {\n" + "          \"type\": \"node\",\n" + "          \"label\": " +
			"\"Experten empfehlen\",\n" + "          \"children\": [\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Alle Empfehlungen\",\n" + "         " +
			"     \"url\": \"http://www.mytoys.de/c/expertenempfehlungen.html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"4 " +
			"Bücher\",\n" + "              \"url\": \"http://www.mytoys.de/c/experten-stephanie-buecher-4-empfehlungen.html\"\n" + "            },\n" + "            {\n" + "              \"type\": " +
			"\"link\",\n" + "              \"label\": \"4 KiGa- und Schulprodukte\",\n" + "              \"url\": \"http://www.mytoys.de/c/experten-angela-schule-basteln-4-empfehlungen.html\"\n" + "" +
			"            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"4 Spiele\",\n" + "              \"url\": \"http://www.mytoys" +
			".de/c/experten-saskia-spiele-puzzle-4-empfehlungen.html\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"4 " +
			"Spielsachen\",\n" + "              \"url\": \"http://www.mytoys.de/c/experten-anett-tanja-bernhard-spielzeug-4-empfehlungen.html\"\n" + "            }\n" + "          ]\n" + "        }," +
			"\n" + "        {\n" + "          \"type\": \"node\",\n" + "          \"label\": \"Alles für die Windeltorte\",\n" + "          \"children\": [\n" + "            {\n" + "              " +
			"\"type\": \"link\",\n" + "              \"label\": \"\\\"Die Eiskönigin\\\" Windeltorte\",\n" + "              \"url\": \"http://www.mytoys.de/mkt/eiskoenigin-windeltorte\"\n" + "      " +
			"      },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Star Wars Windeltorte\",\n" + "              \"url\": \"http://www.mytoys" +
			".de/mkt/star-wars-windeltorte\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": \"Winnie Puuh Windeltorte\",\n" + "      " +
			"        \"url\": \"http://www.mytoys.de/mkt/winnie-puuh-windeltorte\"\n" + "            },\n" + "            {\n" + "              \"type\": \"link\",\n" + "              \"label\": " +
			"\"Kunterbunte Windeltorte\",\n" + "              \"url\": \"http://www.mytoys.de/mkt/windeltorte-kunterbunt\"\n" + "            },\n" + "            {\n" + "              \"type\": " +
			"\"link\",\n" + "              \"label\": \"Für Jungen\",\n" + "              \"url\": \"http://www.mytoys.de/mkt/windeltorte-jungen\"\n" + "            },\n" + "            {\n" + "    " +
			"          \"type\": \"link\",\n" + "              \"label\": \"Für Mädchen\",\n" + "              \"url\": \"http://www.mytoys.de/mkt/windeltorte-maedchen\"\n" + "            }\n" + "  " +
			"        ]\n" + "        }\n" + "      ]\n" + "    },\n" + "    {\n" + "      \"type\": \"section\",\n" + "      \"label\": \"KombiShopping\",\n" + "      \"children\": [\n" + "        " +
			"{\n" + "          \"type\": \"external-link\",\n" + "          \"label\": \"mirapodo\",\n" + "          \"url\": \"http://www.mirapodo.de/\"\n" + "        },\n" + "        {\n" + "     " +
			"     \"type\": \"external-link\",\n" + "          \"label\": \"ambellis\",\n" + "          \"url\": \"http://www.ambellis.de\"\n" + "        },\n" + "        {\n" + "          \"type\":" +
			" \"link\",\n" + "          \"label\": \"So funktioniert KombiShopping\",\n" + "          \"url\": \"http://www.mytoys.de/mkt/kombishopping\"\n" + "        }\n" + "      ]\n" + "    }," +
			"\n" + "    {\n" + "      \"type\": \"section\",\n" + "      \"label\": \"Service\",\n" + "      \"children\": [\n" + "        {\n" + "          \"type\": \"link\",\n" + "          " +
			"\"label\": \"Hilfe\",\n" + "          \"url\": \"http://www.mytoys.de/c/faq.html\"\n" + "        },\n" + "        {\n" + "          \"type\": \"link\",\n" + "          \"label\": \"Mein" +
			" Konto\",\n" + "          \"url\": \"https://checkout.mytoys.de/checkout/serviceOverview\"\n" + "        },\n" + "        {\n" + "          \"type\": \"link\",\n" + "          " +
			"\"label\": \"Kontakt\",\n" + "          \"url\": \"http://www.mytoys.de/c/kontakt.html\"\n" + "        },\n" + "        {\n" + "          \"type\": \"link\",\n" + "          \"label\": " +
			"\"Unsere Filialen\",\n" + "          \"url\": \"http://www.mytoys.de/c/filialen.html\"\n" + "        },\n" + "        {\n" + "          \"type\": \"external-link\",\n" + "          " +
			"\"label\": \"myToys Blog\",\n" + "          \"url\": \"http://www.mytoys.de/my-blog/\"\n" + "        },\n" + "        {\n" + "          \"type\": \"external-link\",\n" + "          " +
			"\"label\": \"myToys bei Facebook\",\n" + "          \"url\": \"https://www.facebook.com/myToys\"\n" + "        },\n" + "        {\n" + "          \"type\": \"external-link\",\n" + "    " +
			"      \"label\": \"Jobs bei myToys\",\n" + "          \"url\": \"https://mytoysgroup.jobs/\"\n" + "        }\n" + "      ]\n" + "    }\n" + "  ]\n" + "}";

	public MockService(BehaviorDelegate<Service> delegate) {
		this.mDelegate = delegate;
	}

	public Observable<NavigationEntries> getNavigationEntries() {
		return mDelegate.returningResponse(new Gson().fromJson(FEEDS, NavigationEntries.class))
		                .getNavigationEntries();
	}
}
