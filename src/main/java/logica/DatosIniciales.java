package logica;

import java.util.ArrayList;
import modelo.Videojuego;

public class DatosIniciales {

    public static ArrayList<Videojuego> cargar() {
        ArrayList<Videojuego> lista = new ArrayList<>();

        lista.add(new Videojuego(2001, "Hollow Knight",          "Team Cherry",          "Plataformas", 2017, 45));
        lista.add(new Videojuego(1580, "Celeste",                "Maddy Makes Games",    "Plataformas", 2018, 38));
        lista.add(new Videojuego(2750, "Stardew Valley",         "ConcernedApe",         "Simulación",  2016, 50));
        lista.add(new Videojuego(1320, "Hades",                  "Supergiant Games",     "Acción",      2020, 45));
        lista.add(new Videojuego(2410, "Into the Breach",        "Subset Games",         "Estrategia",  2018, 30));
        lista.add(new Videojuego(1999, "Undertale",              "Toby Fox",             "RPG",         2015, 42));
        lista.add(new Videojuego(2215, "Dead Cells",             "Motion Twin",          "Acción",      2018, 30));
        lista.add(new Videojuego(1105, "Bastion",                "Supergiant Games",     "Acción",      2011, 25));
        lista.add(new Videojuego(2890, "FTL: Faster Than Light", "Subset Games",         "Estrategia",  2012, 27));
        lista.add(new Videojuego(1760, "Shovel Knight",          "Yacht Club Games",     "Plataformas", 2014, 33));
        lista.add(new Videojuego(2533, "Disco Elysium",          "ZA/UM",                "RPG",         2019, 40));
        lista.add(new Videojuego(1450, "Papers, Please",         "3909 LLC",             "Simulación",  2013, 20));
        lista.add(new Videojuego(2650, "Cuphead",                "Studio MDHR",          "Plataformas", 2017, 36));
        lista.add(new Videojuego(1888, "Slay the Spire",         "Mega Crit",            "Estrategia",  2019, 41));
        lista.add(new Videojuego(2300, "Terraria",               "Re-Logic",             "Acción",      2011, 48));
        lista.add(new Videojuego(1234, "Frostpunk",              "11 bit studios",       "Estrategia",  2018, 29));
        lista.add(new Videojuego(2777, "Omori",                  "Omocat",               "RPG",         2020, 33));
        lista.add(new Videojuego(1666, "Spiritfarer",            "Thunder Lotus Games",  "Simulación",  2020, 26));
        lista.add(new Videojuego(2999, "Hades II",               "Supergiant Games",     "Acción",      2025, 39));
        lista.add(new Videojuego(1555, "Vampire Survivors",      "poncle",               "Acción",      2022, 44));

        return lista;
    }
}
