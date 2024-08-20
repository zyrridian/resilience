package com.zkylab.common;

import com.zkylab.data.Progress;
import com.zkylab.entity.Entity;

/**
 * Handles events such as dialogues, teleportation, and interactions in the
 * game.
 */
public class EventHandler {

    GamePanel gamePanel; // Game panel reference
    EventRect eventRect[][][]; // 3D array for event rectangles
    public static Entity eventMaster; // Entity responsible for dialogues and other events
    int previousEventX, previousEventY; // Previous event coordinates
    public boolean monsterMode = false;

    // Flags and temporary variables
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    /**
     * Constructor to initialize the event handler.
     *
     * @param gamePanel The GamePanel instance to interact with the game world.
     */
    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        eventMaster = new Entity(gamePanel);

        // Initialize the eventRect array
        eventRect = new EventRect[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        initializeEventRect();

        // Set default dialogues
        setDialogue();
    }

    public void initializeEventRect() {
        int map = 0, col = 0, row = 0;
        while (map < gamePanel.maxMap && col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
                if (row == gamePanel.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    /**
     * Sets default dialogues for various events.
     */
    public void setDialogue() {
        eventMaster.dialogues[0][0] = "Ada orang yang terlihat kebingungan.";
        eventMaster.dialogues[0][1] = "Bicaralah padanya.";
        eventMaster.dialogues[0][2] = "Atau abaikan saja.";

        eventMaster.dialogues[1][0] = "Kamu meminum air. Energi dipulihkan.";
        eventMaster.dialogues[1][1] = "(Progress telah tersimpan)";

        // Ruang Utama
        eventMaster.dialogues[2][0] = "Pintunya terkunci.";
        eventMaster.dialogues[2][1] = "Ada sesuatu yang menghalangi.";
        eventMaster.dialogues[2][2] = "Bahkan kunci pun tidak bisa digunakan.";
        eventMaster.dialogues[3][0] = "Sebuah tanaman.";
        eventMaster.dialogues[3][1] = "Terbuat dari plastik.";
        eventMaster.dialogues[4][0] = "Tidak ada acara menarik di televisi.";
        eventMaster.dialogues[4][1] = "Membosankan.";
        eventMaster.dialogues[5][0] = "Sofa yang nyaman.";

        // Dapur
        eventMaster.dialogues[6][0] = "Sebuah meja dengan noda yang tidak bisa dihapus.";
        eventMaster.dialogues[7][0] = "Kulkas ini berdengung dengan nada rendah.";
        eventMaster.dialogues[7][1] = "Isinya hanya air.";
        eventMaster.dialogues[8][0] = "Oven tua ini masih panas.";
        eventMaster.dialogues[8][1] = "Meski tidak ada yang menggunakannya.";
        eventMaster.dialogues[9][0] = "Airnya tidak berhenti menetes.";
        eventMaster.dialogues[9][1] = "Kamu mencoba memperbaikinya.";
        eventMaster.dialogues[9][2] = "...";
        eventMaster.dialogues[9][3] = "Kerja bagus.";
        eventMaster.dialogues[9][4] = "Sekarang keran airnya patah.";
        eventMaster.dialogues[9][5] = "Tapi...";
        eventMaster.dialogues[9][6] = "kenapa airnya berhenti mengalir?";
        eventMaster.dialogues[10][0] = "Kompor tua dengan tungku-tungku yang menghitam.";

        // Main Town
        eventMaster.dialogues[11][0] = "Pohon hijau yang menyejukkan udara.";
        eventMaster.dialogues[12][0] = "Apakah ini kolam renang?";
        eventMaster.dialogues[12][1] = "Atau sumber air minum?";

        eventMaster.dialogues[13][0] = "Kamu menemukan sebuah air mancur.";
        eventMaster.dialogues[13][1] = "Airnya sangat segar.";
        eventMaster.dialogues[13][2] = "Hingga membuatmu tidak sengaja meminumnya.";

        eventMaster.dialogues[14][0] = "Kamu tidak bisa kembali ke kota.";
        eventMaster.dialogues[14][1] = "Teruslah berjalan maju.";

        // Library
        eventMaster.dialogues[15][0] = "Saat kamu menyentuh rak buku ini, sebuah buku dengan sampul\nmengkilap menarik perhatianmu.";
        eventMaster.dialogues[15][1] = "Kamu membacanya:";
        eventMaster.dialogues[15][2] = "\"Trivia: Quantum Computing adalah jenis komputasi yang\nmenggunakan prinsip-prinsip mekanika kuantum.\"";
        eventMaster.dialogues[15][3] = "\"Dengan menggunakan qubit, komputer kuantum dapat memproses\ninformasi jauh lebih cepat daripada komputer klasik pada\nbeberapa tugas tertentu.\"";

        eventMaster.dialogues[16][0] = "Kamu membuka buku berjudul \"Kosmos\" karya Carl Sagan.";
        eventMaster.dialogues[16][1] = "Di dalamnya, kamu membaca:";
        eventMaster.dialogues[16][2] = "\"Inti dari teori relativitas Einstein adalah bahwa waktu\ndan ruang tidaklah mutlak.\"";
        eventMaster.dialogues[16][3] = "\"Mereka relatif terhadap kecepatan benda dan medan\ngravitasi yang dialami.\"";
        eventMaster.dialogues[16][4] = "\"Ini membuka pintu bagi banyak konsep baru dalam fisika\nmodern.\"";

        eventMaster.dialogues[17][0] = "Kamu menemukan buku berjudul \"Molekul yang Mengubah Dunia\".";
        eventMaster.dialogues[17][1] = "Saat membukanya, kamu membaca:";
        eventMaster.dialogues[17][2] = "\"Penemuan penicillin oleh Alexander Fleming pada tahun 1928\nmerevolusi dunia medis.\"";
        eventMaster.dialogues[17][3] = "\"Antibiotik ini menyelamatkan jutaan nyawa dan menjadi\ndasar pengembangan obat-obatan antibiotik lainnya.\"";

        eventMaster.dialogues[18][0] = "Di rak ini, kamu menemukan buku berjudul \"Rise of the\nRobots\"";
        eventMaster.dialogues[18][1] = "Saat membacanya, kamu menemukan:";
        eventMaster.dialogues[18][2] = "\"Trivia: Algoritma pembelajaran mendalam (deep learning)\nyang digunakan dalam kecerdasan buatan terinspirasi oleh\ncara kerja otak manusia, khususnya jaringan neuron.\"";
        eventMaster.dialogues[18][3] = "\"Ini memungkinkan komputer untuk mengenali pola dan belajar\ndari data dengan cara yang menakjubkan.\"";

        eventMaster.dialogues[19][0] = "Kamu melihat sebuah buku berjudul \"Meditations\" oleh Marcus\nAurelius.";
        eventMaster.dialogues[19][1] = "Kamu membaca salah satu bagiannya:";
        eventMaster.dialogues[19][2] = "\"Filsuf Romawi Marcus Aurelius percaya bahwa kita harus\nmenjalani hidup dengan kebajikan, sesuai dengan alam,\"";
        eventMaster.dialogues[19][2] = "\"dan menerima hal-hal yang berada di luar kendali kita\ndengan tenang.\"";
        eventMaster.dialogues[19][3] = "\"Pandangannya tentang stoisisme masih relevan hingga hari\nini.\"";

        eventMaster.dialogues[20][0] = "Kamu mengambil buku berjudul \"The Elegant Universe\" karya\nBrian Greene.";
        eventMaster.dialogues[20][1] = "Saat membacanya, kamu menemukan:";
        eventMaster.dialogues[20][2] = "\"Teori string adalah salah satu konsep yang paling menarik\ndalam fisika modern.\"";
        eventMaster.dialogues[20][3] = "\"Ia mengusulkan bahwa partikel dasar alam semesta\nsebenarnya adalah 'tali' satu dimensi yang bergetar pada\nfrekuensi tertentu,\"";
        eventMaster.dialogues[20][3] = "\"yang menciptakan berbagai partikel subatomik.\"";

        eventMaster.dialogues[21][0] = "Kamu membuka buku berjudul \"The History of Time\".";
        eventMaster.dialogues[21][1] = "Di dalamnya, kamu membaca:";
        eventMaster.dialogues[21][2] = "\"Tahukah kamu? Candra Gupta Maurya adalah pendiri\nKekaisaran Maurya di India kuno sekitar tahun 322 SM.\"";
        eventMaster.dialogues[21][3] = "\"Dia berhasil menyatukan hampir seluruh anak benua India\ndi bawah satu pemerintahan.\"";

        eventMaster.dialogues[22][0] = "Kamu menemukan buku berjudul \"Wealth of Nations\" oleh Adam\nSmith.";
        eventMaster.dialogues[22][1] = "Saat membacanya, kamu mengetahui:";
        eventMaster.dialogues[22][2] = "\"Adam Smith dikenal sebagai bapak ekonomi modern.\"";
        eventMaster.dialogues[22][3] = "\"Dalam bukunya, ia memperkenalkan konsep 'tangan tak\nterlihat' yang menggambarkan bagaimana individu yang\nmengejar kepentingan pribadi,\"";
        eventMaster.dialogues[22][4] = "\"secara tidak langsung berkontribusi pada kesejahteraan\nekonomi seluruh masyarakat.\"";

        eventMaster.dialogues[23][0] = "Kamu mengambil buku berjudul \"The Story of Art\" oleh E.H.\nGombrich.";
        eventMaster.dialogues[23][1] = "Kamu membaca:";
        eventMaster.dialogues[23][2] = "\"Salah satu karya seni paling terkenal di dunia adalah\n'Mona Lisa' karya Leonardo da Vinci.\"";
        eventMaster.dialogues[23][3] = "\"Lukisan ini terkenal karena senyum misterius Mona Lisa\ndan penggunaan teknik sfumato yang canggih oleh da Vinci.\"";

        eventMaster.dialogues[24][0] = "Di rak ini, kamu menemukan buku berjudul \"Silent Spring\"\noleh Rachel Carson.";
        eventMaster.dialogues[24][1] = "Saat membacanya, kamu mengetahui:";
        eventMaster.dialogues[24][2] = "\"Buku ini mengungkap dampak negatif penggunaan pestisida\nterhadap lingkungan.\"";
        eventMaster.dialogues[24][3] = "\"'Silent Spring' memainkan peran penting dalam memulai\ngerakan lingkungan modern dan mendorong perubahan dalam\nkebijakan lingkungan.\"";

        eventMaster.dialogues[25][0] = "Kamu menemukan buku berjudul \"Thinking, Fast and Slow\"\noleh Daniel Kahneman.";
        eventMaster.dialogues[25][1] = "Kamu membaca:";
        eventMaster.dialogues[25][2] = "\"Kahneman memperkenalkan dua sistem berpikir manusia:\nSistem 1 yang cepat dan intuitif, serta Sistem 2 yang\nlambat dan analitis.\"";
        eventMaster.dialogues[25][3] = "\"Pemahaman ini membantu kita menyadari bagaimana kita\nmembuat keputusan sehari-hari.\"";

        eventMaster.dialogues[26][0] = "Kamu membuka buku berjudul \"Myths of the Norsemen\".";
        eventMaster.dialogues[26][1] = "Di dalamnya, kamu membaca:";
        eventMaster.dialogues[26][2] = "\"Dalam mitologi Nordik, Yggdrasil adalah pohon dunia yang\nmenghubungkan sembilan dunia.\"";
        eventMaster.dialogues[26][3] = "\"Akar-akarnya menjangkau hingga ke berbagai alam, termasuk\nAsgard, tempat tinggal para dewa, dan Hel, dunia kematian.\"";

        eventMaster.dialogues[27][0] = "Kamu menemukan buku berjudul \"1984\" oleh George Orwell.";
        eventMaster.dialogues[27][1] = "Saat membacanya, kamu mengetahui:";
        eventMaster.dialogues[27][2] = "\"Dystopian novel ini menggambarkan masyarakat totaliter di\nmana pemerintah mengendalikan setiap aspek kehidupan\nindividu, termasuk pikiran mereka.\"";
        eventMaster.dialogues[27][3] = "\"Buku ini menawarkan kritik tajam terhadap pengawasan dan\nkehilangan kebebasan.\"";

        eventMaster.dialogues[28][0] = "Di rak ini, kamu menemukan buku berjudul \"Sapiens: A Brief\nHistory of Humankind\" oleh Yuval Noah Harari.";
        eventMaster.dialogues[28][1] = "Saat membacanya, kamu mengetahui:";
        eventMaster.dialogues[28][2] = "\"Harari menjelaskan bagaimana Homo sapiens, spesies kita,\nberhasil menjadi spesies dominan di bumi.\"";
        eventMaster.dialogues[28][3] = "\"Salah satu faktor kunci adalah kemampuan kita untuk\nmenciptakan dan percaya pada narasi kolektif, seperti\nmitos, agama, dan hukum.\"";

        eventMaster.dialogues[29][0] = "Kamu mengambil buku berjudul \"A Pattern Language\" oleh\nChristopher Alexander.";
        eventMaster.dialogues[29][1] = "Kamu membaca:";
        eventMaster.dialogues[29][2] = "\"Buku ini memperkenalkan konsep pola dalam desain\narsitektur.\"";
        eventMaster.dialogues[29][3] = "\"Setiap pola menggambarkan solusi desain yang telah\nterbukti efektif dalam menciptakan lingkungan yang nyaman\ndan fungsional.\"";

        eventMaster.dialogues[30][0] = "Kamu membuka buku berjudul \"Gödel, Escher, Bach\" oleh\nDouglas Hofstadter.";
        eventMaster.dialogues[30][1] = "Di dalamnya, kamu membaca:";
        eventMaster.dialogues[30][2] = "\"Buku ini mengeksplorasi hubungan antara karya-karya\natematikawan Kurt Gödel, seniman M.C. Escher, dan komponis\nJohann Sebastian Bach.\"";
        eventMaster.dialogues[30][3] = "\"Melalui pola-pola yang kompleks dan interkoneksi,\nHofstadter mengungkapkan keindahan di balik logika dan\nkreativitas.\"";

        eventMaster.dialogues[31][0] = "Di rak ini, kamu menemukan buku berjudul \"Brief Answers\nto the Big Questions\" oleh Stephen Hawking.";
        eventMaster.dialogues[31][1] = "Saat membacanya, kamu mengetahui:";
        eventMaster.dialogues[31][2] = "\"Hawking menjawab pertanyaan-pertanyaan besar tentang\nasal mula alam semesta, keberadaan Tuhan, dan masa depan\numat manusia.\"";
        eventMaster.dialogues[31][3] = "\"Buku ini menggabungkan pemikiran ilmiah Hawking dengan\nrefleksi pribadinya.\"";

        eventMaster.dialogues[32][0] = "Kamu mengambil buku berjudul \"The Selfish Gene\" oleh\nRichard Dawkins.";
        eventMaster.dialogues[32][1] = "Kamu membaca:";
        eventMaster.dialogues[32][2] = "\"Dawkins memperkenalkan konsep bahwa gen adalah unit\nseleksi alam yang utama.\"";
        eventMaster.dialogues[32][3] = "\"Buku ini menjelaskan bagaimana evolusi bekerja pada\ntingkat genetik dan bagaimana perilaku altruistik bisa\nbermunculan.\"";

        eventMaster.dialogues[33][0] = "Kamu membuka buku berjudul \"Guns, Germs, and Steel\" oleh\nJared Diamond.";
        eventMaster.dialogues[33][1] = "Di dalamnya, kamu membaca:";
        eventMaster.dialogues[33][2] = "\"Diamond menjelaskan faktor-faktor geografi dan\nlingkungan yang mempengaruhi perkembangan peradaban\nmanusia.\"";
        eventMaster.dialogues[33][3] = "\"Buku ini menawarkan perspektif baru tentang sejarah\nmanusia dan perbedaan kekayaan antara berbagai masyarakat.\"";

        eventMaster.dialogues[34][0] = "Di rak ini, kamu menemukan buku berjudul \"Biocentrism:\nHow Life and Consciousness are the Keys to Understanding\nthe True Nature of the Universe\" oleh Robert Lanza.";
        eventMaster.dialogues[34][1] = "Saat membacanya, kamu mengetahui:";
        eventMaster.dialogues[34][2] = "\"Lanza mengajukan teori bahwa kehidupan dan kesadaran\nadalah pusat dari realitas kita.\"";
        eventMaster.dialogues[34][3] = "\"Menurut biocentrism, alam semesta tidak bisa eksis\nterlepas dari kesadaran makhluk hidup.\"";

        eventMaster.dialogues[35][0] = "Kamu menyalakan komputer dan melihat layar login.";
        eventMaster.dialogues[35][1] = "Di sudut layar, ada catatan kecil yang bertuliskan:";
        eventMaster.dialogues[35][2] = "\"Jangan lupa mengganti kata sandi setiap 30 hari.\"";
        eventMaster.dialogues[35][3] = "\"Terakhir diperbarui: 29 hari yang lalu.\"";

        eventMaster.dialogues[36][0] = "Kamu mengakses komputer dan menemukan layar yang penuh\ndengan folder.";
        eventMaster.dialogues[36][1] = "Salah satu folder memiliki nama yang mencurigakan:";
        eventMaster.dialogues[36][2] = "\"Folder Rahasia: Jangan Dibuka!\"";

        eventMaster.dialogues[37][0] = "Kamu menyalakan komputer dan mendapati bahwa wallpaper\ndesktop-nya adalah gambar pemandangan alam yang indah.";
        eventMaster.dialogues[37][1] = "Ada catatan di sticky note virtual:";
        eventMaster.dialogues[37][2] = "\"Jangan lupa minum air! Jaga kesehatanmu, bahkan saat\nbelajar.\"";

        eventMaster.dialogues[38][0] = "Kamu membuka lemari dan menemukan koleksi buku catatan yang\nrapi.";
        eventMaster.dialogues[38][1] = "Di salah satu buku, kamu melihat tulisan tangan yang indah\ndengan berbagai catatan pribadi dan sketsa.";
        eventMaster.dialogues[38][2] = "Tampaknya ini adalah jurnal milik pustakawan.";

        eventMaster.dialogues[39][0] = "Kamu mendekati kolam air yang tenang di sudut perpustakaan.";
        eventMaster.dialogues[39][1] = "Airnya jernih, dan kamu melihat ikan-ikan kecil berenang\ndengan anggun.";
        eventMaster.dialogues[39][2] = "Di dasar kolam, ada ukiran samar bertuliskan:";
        eventMaster.dialogues[39][3] = "\"Semoga ketenangan kolam ini memberikan kedamaian bagi\npikiran yang lelah dan inspirasi bagi jiwa yang mencari.\"";

        eventMaster.dialogues[40][0] = "Jangan mendekat.";
        eventMaster.dialogues[40][1] = "Area ini terlarang.";

        eventMaster.dialogues[41][0] = "Sudah kubilang berhenti.";
        eventMaster.dialogues[41][1] = "Lebih baik kau mengeksplorasi kota terlebih dahulu.";

        eventMaster.dialogues[42][0] = "Ternyata kau sangat keras kepala.";
        eventMaster.dialogues[42][1] = "Baiklah, konsekuensinya akan kau tanggung sendiri.";

        eventMaster.dialogues[43][0] = "Selamat tinggal.";
        eventMaster.dialogues[43][1] = "Aku tidak akan memperingatimu lagi.";
        eventMaster.dialogues[43][2] = "Kecuali...";
        eventMaster.dialogues[43][3] = "Jika kau menekan tombol NEW GAME. Hahaha.";

    }

    /**
     * Checks for events and triggers the appropriate actions based on player
     * position.
     */
    public void checkEvent() {
        // Check if the player character is more than 1 tile away from the last event
        // int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        // int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        // int distance = Math.max(xDistance, yDistance);

        // if (distance > gamePanel.tileSize) {
        canTouchEvent = true;
        // }

        if (canTouchEvent) {
            handleEvents();
        }

    }

    /**
     * Handles specific events based on player interactions with event rectangles.
     */
    private void handleEvents() {

        // ========== EVENT TELEPORT ========== //
        if (hit(0, 24, 31, "down"))
            teleport(1, 15, 27, GamePanel.OUTSIDE_AREA); // house to town teleport
        if (hit(0, 24, 23, "up"))
            openTheDoor(5, 24, 28, GamePanel.INDOOR_AREA); // house to indoor teleport
        if (hit(5, 24, 28, "down"))
            openTheDoor(0, 24, 28, GamePanel.INDOOR_AREA); // indoor to house teleport
        if (hit(1, 15, 27, "up"))
            teleport(0, 24, 31, GamePanel.OUTSIDE_AREA); // town to house teleport

        else if (hit(0, 36, 19, "right") || hit(0, 36, 20, "right"))
            teleport(4, 15, 27, GamePanel.OUTSIDE_AREA); // Teleport kota 1 ke kota 5

        else if (hit(1, 25, 31, "down") || hit(1, 26, 31, "down"))
            teleport(0, 28, 14, GamePanel.OUTSIDE_AREA); // Teleport kota 2 ke kota 1
        else if (hit(1, 34, 21, "right") || hit(1, 34, 22, "right"))
            teleport(2, 14, 27, GamePanel.OUTSIDE_AREA); // Teleport kota 2 ke kota 3

        else if (hit(2, 14, 27, "left") || hit(2, 14, 28, "left"))
            teleport(1, 34, 21, GamePanel.OUTSIDE_AREA); // Teleport kota 3 ke kota 2
        else if (hit(2, 33, 27, "right") || hit(2, 33, 28, "right"))
            teleport(3, 12, 23, GamePanel.OUTSIDE_AREA); // Teleport kota 3 ke kota 4

        else if (hit(3, 12, 23, "left") || hit(3, 12, 24, "left"))
            teleport(2, 33, 27, GamePanel.OUTSIDE_AREA); // Teleport kota 4 ke kota 3
        else if (hit(3, 22, 36, "down") || hit(3, 23, 36, "down"))
            teleport(4, 24, 21, GamePanel.OUTSIDE_AREA); // Teleport kota 4 ke kota 5

        else if (hit(4, 24, 21, "up") || hit(4, 25, 21, "up") || hit(4, 26, 21, "up") || hit(4, 27, 21, "up"))
            teleport(3, 22, 36, GamePanel.OUTSIDE_AREA); // Teleport kota 5 ke kota 4
        else if (hit(4, 18, 26, "left") || hit(4, 18, 27, "left") || hit(4, 18, 28, "left"))
            teleport(0, 36, 19, GamePanel.OUTSIDE_AREA); // Teleport kota 5 ke kota 1

        // ========== RUMAH EVENTS ========== //
        else if (hit(0, 17, 19, "up"))
            openTheDoor(5, 24, 28, GamePanel.INDOOR_AREA); // Teleport kota 1 ke rumah
        else if (hit(5, 24, 28, "down"))
            teleport(0, 17, 19, GamePanel.OUTSIDE_AREA); // Teleport rumah ke kota 1
        else if (hit(5, 18, 28, "left"))
            teleport(6, 28, 27, GamePanel.INDOOR_AREA); // Teleport rumah ke dapur
        else if (hit(6, 28, 27, "right"))
            teleport(5, 18, 28, GamePanel.INDOOR_AREA); // Teleport dapur ke rumah

        else if (hit(0, 20, 19, "up"))
            openTheDoor(7, 24, 35, GamePanel.INDOOR_AREA); // Teleport kota 1 ke perpustakaan
        else if (hit(7, 24, 35, "down") || hit(7, 24, 35, "down"))
            teleport(0, 20, 19, GamePanel.OUTSIDE_AREA); // Teleport rumah ke kota 1

        // Locked door
        else if (hit(0, 26, 19, "up"))
            interactObject(2);
        else if (hit(0, 33, 19, "up"))
            interactObject(2);
        else if (hit(1, 29, 29, "up"))
            interactObject(2);
        else if (hit(2, 19, 27, "up"))
            interactObject(2);
        else if (hit(2, 25, 27, "up"))
            interactObject(2);
        else if (hit(3, 19, 29, "up"))
            interactObject(2);

        // ========== STUDIO EVENTS ========== //
        else if (hit(0, 23, 19, "up"))
            openTheDoor(8, 28, 42, GamePanel.INDOOR_AREA); // Teleport kota 1 ke studio 1
        else if (hit(8, 28, 42, "down"))
            teleport(0, 23, 19, GamePanel.OUTSIDE_AREA); // Teleport studio 1 ke kota 1

        // ========== KITCHEN EVENTS ========== //
        else if (hit(6, 18, 24, "up"))
            interactObject(6);
        else if (hit(6, 19, 24, "up"))
            interactObject(7);
        else if (hit(6, 20, 24, "up"))
            interactObject(8);
        else if (hit(6, 21, 24, "up"))
            interactObject(9);
        else if (hit(6, 22, 24, "up"))
            interactObject(10);

        // ========== LIVING ROOM EVENTS ========== //
        else if (hit(5, 32, 24, "up") || hit(5, 27, 24, "up")) // pintu
            interactObject(2);
        else if (hit(5, 33, 24, "right") || hit(5, 24, 24, "left")) // tanaman
            interactObject(3);
        else if (hit(5, 20, 25, "up") || hit(5, 21, 25, "up") || hit(5, 25, 25, "up")) // tv
            interactObject(4);
        else if (hit(5, 20, 25, "left") || hit(5, 20, 26, "left") || hit(5, 20, 27, "left") || hit(5, 19, 28, "left"))
            interactObject(5);

        // ========== MAIN TOWN EVENTS ========== //
        else if (hit(0, 11, 19, "up") || hit(0, 12, 19, "up") || hit(0, 15, 19, "up")) // pohon
            interactObject(11);
        else if (hit(0, 13, 19, "up") || hit(0, 14, 19, "up"))
            healingPool();
        else if (hit(0, 11, 27, "up") || hit(0, 12, 27, "up") || hit(0, 13, 27, "up") || hit(0, 11, 30, "down")
                || hit(0, 12, 30, "down") || hit(0, 13, 30, "down") || hit(0, 14, 21, "left") || hit(0, 14, 22, "left")
                || hit(0, 14, 23, "left") || hit(0, 14, 24, "left") || hit(0, 14, 25, "left") || hit(0, 14, 26, "left")) // kolam
            interactObject(12);
        else if (hit(1, 19, 22, "down") || hit(1, 20, 22, "down") || hit(1, 22, 22, "down") || hit(1, 23, 22, "down")
                || hit(1, 19, 25, "down") || hit(1, 20, 25, "down") || hit(1, 22, 25, "down") || hit(1, 23, 25, "down")
                || hit(1, 19, 25, "up") || hit(1, 20, 25, "up") || hit(1, 22, 25, "up") || hit(1, 23, 25, "up")
                || hit(1, 19, 28, "up") || hit(1, 20, 28, "up") || hit(1, 22, 28, "up") || hit(1, 23, 28, "up")
                || hit(1, 18, 23, "right") || hit(1, 18, 24, "right") || hit(1, 18, 26, "right")
                || hit(1, 18, 27, "right")
                || hit(1, 21, 23, "right") || hit(1, 21, 24, "right") || hit(1, 21, 26, "right")
                || hit(1, 21, 27, "right")
                || hit(1, 21, 23, "left") || hit(1, 21, 24, "left") || hit(1, 21, 26, "left") || hit(1, 21, 27, "left")
                || hit(1, 24, 23, "left") || hit(1, 24, 24, "left") || hit(1, 24, 26, "left") || hit(1, 24, 27, "left"))
            interactObject(13); // air mancur kota 2

        // ========== MONSTER MAP EVENTS ========== //
        else if (hit(4, 33, 26, "right") || hit(4, 33, 27, "right") || hit(4, 33, 28, "right"))
            teleport(10, 10, 27, GamePanel.OUTSIDE_AREA); // teleport from town05 to monster01
        else if (hit(10, 10, 27, "left") || hit(10, 10, 28, "left"))
            autoDialog(14); // if player want to go back to town05, they can't
        else if (hit(10, 39, 27, "right") || hit(10, 39, 28, "right"))
            teleport(11, 10, 24, GamePanel.OUTSIDE_AREA); // teleport from monster01 to monster02
        else if (hit(11, 10, 24, "left") || hit(11, 10, 25, "left") || hit(11, 10, 26, "left")
                || hit(11, 10, 27, "left"))
            autoDialog(14); // if player want to go back to monster01, they can't
        else if (hit(11, 39, 24, "right") || hit(11, 39, 25, "right") || hit(11, 39, 26, "right")
                || hit(11, 39, 27, "right"))
            teleport(12, 10, 26, GamePanel.DUNGEON_AREA); // teleport from monster02 to monster03
        else if (hit(12, 10, 24, "left") || hit(12, 10, 25, "left") || hit(12, 10, 26, "left")
                || hit(12, 10, 27, "left"))
            autoDialog(14); // if player want to go back to monster01, they can't
        else if (hit(12, 39, 24, "right") || hit(12, 39, 25, "right") || hit(12, 39, 26, "right")
                || hit(12, 39, 27, "right"))
            teleport(13, 5, 26, GamePanel.DUNGEON_AREA); // teleport monster03 to boss01
        else if (hit(13, 5, 24, "left") || hit(13, 5, 25, "left") || hit(13, 5, 26, "left") || hit(13, 5, 27, "left"))
            autoDialog(14); // if player want to go back to monster01, they can't
        else if (hit(13, 28, 23, "right") || hit(13, 28, 24, "right") || hit(13, 31, 23, "left")
                || hit(13, 31, 24, "left") || hit(13, 29, 22, "down") || hit(13, 30, 22, "down")
                || hit(13, 29, 25, "up") || hit(13, 30, 25, "up"))
            healingPool(); // healing pool
        else if (hit(13, 24, 13, "up"))
            teleport(14, 24, 34, GamePanel.DUNGEON_AREA); // teleport from boss01 to boss02
        else if (hit(14, 24, 34, "down"))
            autoDialog(14); // if player want to go back to monster01, they can't
        else if (hit(14, 24, 10, "up"))
            teleport(0, 19, 27, GamePanel.OUTSIDE_AREA); // teleport from boss01 to boss02

        // ========== LIBRARY EVENTS ========== //

        else if (hit(7, 12, 26, "up") || hit(7, 13, 26, "up"))
            interactObject(15); // rak buku
        else if (hit(7, 14, 26, "up") || hit(7, 15, 26, "up"))
            interactObject(16); // rak buku
        else if (hit(7, 16, 26, "up") || hit(7, 17, 26, "up"))
            interactObject(17); // rak buku
        else if (hit(7, 18, 26, "up"))
            interactObject(18); // rak buku

        else if (hit(7, 21, 26, "up") || hit(7, 22, 26, "up"))
            interactObject(19); // rak buku
        else if (hit(7, 23, 26, "up") || hit(7, 24, 26, "up"))
            interactObject(20); // rak buku
        else if (hit(7, 25, 26, "up") || hit(7, 26, 26, "up"))
            interactObject(21); // rak buku
        else if (hit(7, 27, 26, "up") || hit(7, 28, 26, "up"))
            interactObject(22); // rak buku

        else if (hit(7, 12, 16, "up") || hit(7, 13, 16, "up"))
            interactObject(23); // rak buku
        else if (hit(7, 14, 16, "up") || hit(7, 15, 16, "up"))
            interactObject(24); // rak buku
        else if (hit(7, 16, 16, "up") || hit(7, 17, 16, "up"))
            interactObject(25); // rak buku
        else if (hit(7, 18, 16, "up") || hit(7, 19, 16, "up"))
            interactObject(26); // rak buku
        else if (hit(7, 20, 16, "up") || hit(7, 21, 16, "up"))
            interactObject(27); // rak buku
        else if (hit(7, 22, 16, "up") || hit(7, 23, 16, "up"))
            interactObject(28); // rak buku

        else if (hit(7, 26, 16, "up") || hit(7, 27, 16, "up"))
            interactObject(29); // rak buku
        else if (hit(7, 28, 16, "up") || hit(7, 29, 16, "up"))
            interactObject(30); // rak buku
        else if (hit(7, 30, 16, "up") || hit(7, 31, 16, "up"))
            interactObject(31); // rak buku
        else if (hit(7, 32, 16, "up") || hit(7, 33, 16, "up"))
            interactObject(32); // rak buku
        else if (hit(7, 34, 16, "up") || hit(7, 35, 16, "up"))
            interactObject(33); // rak buku
        else if (hit(7, 36, 16, "up") || hit(7, 37, 16, "up"))
            interactObject(34); // rak buku

        else if (hit(7, 22, 16, "down") || hit(7, 22, 19, "up"))
            interactObject(35); // komputer
        else if (hit(7, 24, 16, "down") || hit(7, 25, 16, "down") || hit(7, 24, 19, "up") || hit(7, 25, 19, "up"))
            interactObject(36); // komputer
        else if (hit(7, 27, 16, "down") || hit(7, 27, 19, "up"))
            interactObject(37); // komputer

        else if (hit(7, 31, 26, "up") || hit(7, 32, 26, "up") || hit(7, 33, 26, "up") || hit(7, 34, 26, "up"))
            interactObject(38); // lemari

        else if (hit(7, 34, 26, "right") || hit(7, 34, 27, "right") || hit(7, 34, 28, "right")
                || hit(7, 34, 29, "right") || hit(7, 34, 30, "right") || hit(7, 35, 31, "up") || hit(7, 36, 31, "up")
                || hit(7, 37, 31, "up"))
            interactObject(39); // kolam

        else if (hit(10, 35, 23, "right") || hit(10, 35, 24, "right") || hit(10, 36, 25, "up") || hit(10, 37, 25, "up"))
            healingPool(); // healing monster01
        else if (hit(11, 33, 23, "up") || hit(10, 34, 23, "up"))
            healingPool(); // healing monster02
        else if (hit(12, 13, 24, "up") || hit(10, 14, 24, "up"))
            healingPool(); // healing monster03

        else if (hit(4, 29, 26, "right") || hit(4, 29, 27, "right") || hit(4, 29, 28, "right")) {
            dangerousAreaWarning(4, 29, 26, 40);
            dangerousAreaWarning(4, 29, 27, 40);
            dangerousAreaWarning(4, 29, 28, 40);
        }

        else if (hit(4, 30, 26, "right") || hit(4, 30, 27, "right") || hit(4, 30, 28, "right")) {
            dangerousAreaWarning(4, 30, 26, 41);
            dangerousAreaWarning(4, 30, 27, 41);
            dangerousAreaWarning(4, 30, 28, 41);
        }

        else if (hit(4, 31, 26, "right") || hit(4, 31, 27, "right") || hit(4, 31, 28, "right")) {
            dangerousAreaWarning(4, 31, 26, 42);
            dangerousAreaWarning(4, 31, 27, 42);
            dangerousAreaWarning(4, 31, 28, 42);
        }

        else if (hit(4, 32, 26, "right") || hit(4, 32, 27, "right") || hit(4, 32, 28, "right")) {
            dangerousAreaWarning(4, 32, 26, 43);
            dangerousAreaWarning(4, 32, 27, 43);
            dangerousAreaWarning(4, 32, 28, 43);
        }
    }

    /**
     * Checks if the player is interacting with a specific event rectangle.
     *
     * @param map          The map index.
     * @param col          The column index.
     * @param row          The row index.
     * @param reqDirection The required direction for the interaction.
     * @return true if the player is interacting with the event rectangle; false
     *         otherwise.
     */
    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gamePanel.currentMap) {
            gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
            gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
            eventRect[map][col][row].x = col * gamePanel.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gamePanel.tileSize + eventRect[map][col][row].y;

            if (gamePanel.player.solidArea.intersects(eventRect[map][col][row])
                    && !eventRect[map][col][row].eventDone) {
                if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                    previousEventX = gamePanel.player.worldX;
                    previousEventY = gamePanel.player.worldY;

                }
            }

            // Reset areas to default
            gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
            gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;

        }

        return hit;
    }

    public void autoDialog(int dialogue) {
        gamePanel.gameState = GamePanel.DIALOGUE_STATE;
        gamePanel.player.attackCanceled = true;
        eventMaster.startDialogue(eventMaster, dialogue);
    }

    public void openTheDoor(int map, int col, int row, int gameState) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.player.attackCanceled = true;
            teleport(map, col, row, gameState);
        }
    }

    /**
     * Handles interactions with objects.
     *
     * @param col       The column index.
     * @param row       The row index.
     * @param gameState The game state to transition to.
     * @param dialog    The dialogue index to display.
     */
    public void interactObject(int dialog) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = GamePanel.DIALOGUE_STATE;
            gamePanel.player.attackCanceled = true;
            gamePanel.playSoundEffect(12);
            eventMaster.startDialogue(eventMaster, dialog);
        }
    }

    /**
     * Handles damage pit interactions.
     *
     * @param col       The column index.
     * @param row       The row index.
     * @param gameState The game state to transition to.
     */
    public void damagePit(int col, int row, int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.playSoundEffect(1);
        eventMaster.startDialogue(eventMaster, 0);
        gamePanel.player.life -= 1;
        eventRect[0][col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void dangerousAreaWarning(int map, int col, int row, int dialogue) {
        gamePanel.gameState = GamePanel.DIALOGUE_STATE;
        // gamePanel.playSoundEffect(1);
        eventMaster.startDialogue(eventMaster, dialogue);
        eventRect[map][col][row].eventDone = true;
        canTouchEvent = false;
    }

    /**
     * Handles healing pool interactions.
     *
     * @param col       The column index.
     * @param row       The row index.
     * @param gameState The game state to transition to.
     */
    public void healingPool() {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = GamePanel.DIALOGUE_STATE;
            gamePanel.player.attackCanceled = true;
            gamePanel.playSoundEffect(2);
            eventMaster.startDialogue(eventMaster, 1);
            gamePanel.player.life = gamePanel.player.maxLife;
            gamePanel.player.mana = gamePanel.player.maxMana;
            // gamePanel.assetSetter.setMonster(); // optional for testing purpose. reset
            // monster
            gamePanel.saveLoad.save();
        }
    }

    /**
     * Handles teleportation events.
     *
     * @param map  The target map index.
     * @param col  The target column index.
     * @param row  The target row index.
     * @param area The target area to transition to.
     */
    public void teleport(int map, int col, int row, int area) {
        gamePanel.gameState = GamePanel.TRANSITION_STATE;
        gamePanel.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gamePanel.playSoundEffect(14);
    }

    /**
     * Initiates dialogue with an entity.
     *
     * @param entity The entity to speak with.
     */
    public void speak(Entity entity) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = GamePanel.DIALOGUE_STATE;
            gamePanel.player.attackCanceled = true;
            entity.speak();
        }
    }

    /**
     * Handles the skeleton lord cutscene event.
     */
    public void skeletonLord() {
        if (!gamePanel.bossBattleOn && !Progress.skeletonLordDefeated) {
            gamePanel.gameState = GamePanel.CUTSCENE_STATE;
            gamePanel.cManager.sceneNumber = gamePanel.cManager.skeletonLord;
        }
    }

}
