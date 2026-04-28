# Book CRUD - Aplikasi Android SQLite

Aplikasi Android sederhana untuk mengelola data buku menggunakan SQLite database dengan operasi CRUD (Create, Read, Update, Delete).

## Fitur

- **Tambah Buku** - Menambahkan buku baru dengan judul, penulis, tahun terbit, dan ISBN
- **Lihat Daftar Buku** - Menampilkan semua buku dalam RecyclerView dengan CardView
- **Edit Buku** - Mengubah data buku yang sudah ada
- **Hapus Buku** - Menghapus buku dengan konfirmasi dialog
- **Cari Buku** - Mencari buku berdasarkan judul atau penulis

## Teknologi

- **Bahasa**: Java
- **Database**: SQLite
- **UI Components**: Material Design, RecyclerView, CardView, FloatingActionButton
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## Struktur Proyek

```
app/src/main/java/com/example/bookcrud/
├── model/
│   └── Book.java              # Model data buku
├── database/
│   └── DatabaseHelper.java    # SQLite helper untuk CRUD operations
├── adapter/
│   └── BookAdapter.java       # RecyclerView adapter
├── MainActivity.java          # Halaman utama (daftar buku)
└── AddEditBookActivity.java   # Halaman tambah/edit buku
```

## Cara Menjalankan

1. Clone repository ini
2. Buka dengan Android Studio
3. Sync Gradle
4. Run di emulator atau device Android

## Screenshot

Aplikasi menampilkan:
- Halaman utama dengan daftar buku dan fitur pencarian
- Form tambah/edit buku dengan validasi input
- Dialog konfirmasi hapus buku

## Build

```bash
./gradlew assembleDebug
```

## License

MIT License
