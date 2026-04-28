# Book CRUD - Aplikasi Android SQLite (Clean Architecture)

Aplikasi Android untuk mengelola data buku menggunakan SQLite database dengan operasi CRUD (Create, Read, Update, Delete). Dibangun menggunakan pola **Clean Architecture**.

## Fitur

- **Tambah Buku** - Menambahkan buku baru dengan judul, penulis, tahun terbit, dan ISBN
- **Lihat Daftar Buku** - Menampilkan semua buku dalam RecyclerView dengan CardView
- **Edit Buku** - Mengubah data buku yang sudah ada
- **Hapus Buku** - Menghapus buku dengan konfirmasi dialog
- **Cari Buku** - Mencari buku berdasarkan judul atau penulis

## Teknologi

- **Bahasa**: Java
- **Arsitektur**: Clean Architecture
- **Database**: SQLite
- **UI Components**: Material Design, RecyclerView, CardView, FloatingActionButton
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## Struktur Proyek (Clean Architecture)

```
app/src/main/java/com/example/bookcrud/
│
├── domain/                          # Domain Layer (Business Logic)
│   ├── entity/
│   │   └── Book.java                # Domain entity (immutable)
│   ├── repository/
│   │   └── BookRepository.java      # Repository interface (contract)
│   └── usecase/
│       ├── GetAllBooksUseCase.java
│       ├── GetBookByIdUseCase.java
│       ├── InsertBookUseCase.java
│       ├── UpdateBookUseCase.java
│       ├── DeleteBookUseCase.java
│       └── SearchBooksUseCase.java
│
├── data/                            # Data Layer (Implementation)
│   ├── local/
│   │   ├── database/
│   │   │   └── BookDatabaseHelper.java   # SQLite helper (singleton)
│   │   ├── dao/
│   │   │   └── BookDao.java              # Data Access Object
│   │   ├── entity/
│   │   │   └── BookEntity.java           # Data entity (mutable)
│   │   └── mapper/
│   │       └── BookMapper.java           # Entity <-> Domain mapper
│   └── repository/
│       └── BookRepositoryImpl.java       # Repository implementation
│
├── presentation/                    # Presentation Layer (UI)
│   ├── booklist/
│   │   ├── BookListActivity.java         # Daftar buku (main screen)
│   │   └── BookListViewModel.java        # ViewModel untuk daftar buku
│   ├── addeditbook/
│   │   ├── AddEditBookActivity.java      # Form tambah/edit buku
│   │   └── AddEditBookViewModel.java     # ViewModel untuk form buku
│   └── adapter/
│       └── BookAdapter.java              # RecyclerView adapter
│
└── di/
    └── Injection.java               # Manual Dependency Injection
```

### Penjelasan Layer

| Layer | Tanggung Jawab |
|-------|---------------|
| **Domain** | Business logic murni. Berisi entity, repository interface, dan use case. Tidak bergantung pada framework Android. |
| **Data** | Implementasi akses data. Berisi SQLite helper, DAO, data entity, mapper, dan implementasi repository. |
| **Presentation** | UI dan interaksi pengguna. Berisi Activity, ViewModel, dan Adapter. |
| **DI** | Dependency injection manual untuk menyediakan instance use case dan repository. |

## Cara Menjalankan

1. Clone repository ini
2. Buka dengan Android Studio
3. Sync Gradle
4. Run di emulator atau device Android

## Build

```bash
./gradlew assembleDebug
```

## License

MIT License
