# Digital Sanctuary - Book CRUD App (Clean Architecture + MVVM)

Aplikasi Android untuk mengelola koleksi buku dengan desain **Dark Purple Theme** menggunakan SQLite database dan pola **Clean Architecture + MVVM**.

## Screenshots

Aplikasi menggunakan tema gelap (dark purple) dengan desain modern termasuk:
- Dashboard dengan "Currently Reading" section
- Grid view library
- Book detail dengan synopsis dan rating
- Profile/Stats page

## Fitur

- **Dashboard Library** - Tampilan "Currently Reading" dengan progress, dan grid "My Library"
- **Tambah Buku** - Form dengan upload cover, genre selector, synopsis
- **Detail Buku** - Cover, genre tags, rating, synopsis, info buku
- **Edit/Hapus Buku** - CRUD lengkap dengan konfirmasi dialog
- **Cari Buku** - Search berdasarkan judul, penulis, atau genre
- **Profil & Statistik** - Total buku, reading goal, top genres
- **Bottom Navigation** - Home, Add, Stats, Profile

## Teknologi

- **Bahasa**: Java
- **Arsitektur**: Clean Architecture + MVVM
- **Database**: SQLite
- **UI**: Material Design, Dark Purple Theme
- **ViewModel**: AndroidX Lifecycle (ViewModel + LiveData)
- **Components**: RecyclerView, CardView, BottomNavigationView, Fragments
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## Struktur Proyek (Clean Architecture + MVVM)

```
app/src/main/java/com/example/bookcrud/
│
├── domain/                              # Domain Layer
│   ├── entity/
│   │   └── Book.java                    # Immutable domain entity
│   ├── repository/
│   │   └── BookRepository.java          # Repository interface
│   └── usecase/
│       ├── GetAllBooksUseCase.java
│       ├── GetBookByIdUseCase.java
│       ├── GetCurrentlyReadingUseCase.java
│       ├── InsertBookUseCase.java
│       ├── UpdateBookUseCase.java
│       ├── DeleteBookUseCase.java
│       └── SearchBooksUseCase.java
│
├── data/                                # Data Layer
│   ├── local/
│   │   ├── database/
│   │   │   └── BookDatabaseHelper.java  # SQLite helper (singleton)
│   │   ├── dao/
│   │   │   └── BookDao.java             # Data Access Object
│   │   ├── entity/
│   │   │   └── BookEntity.java          # Mutable data entity
│   │   └── mapper/
│   │       └── BookMapper.java          # Entity <-> Domain mapper
│   └── repository/
│       └── BookRepositoryImpl.java      # Repository implementation
│
├── presentation/                        # Presentation Layer (MVVM)
│   ├── main/
│   │   ├── MainActivity.java            # Bottom Navigation host
│   │   ├── HomeFragment.java            # Dashboard (Currently Reading + Library)
│   │   └── ProfileFragment.java         # Stats & Profile
│   ├── addeditbook/
│   │   ├── AddEditBookActivity.java     # Form tambah/edit buku
│   │   ├── AddEditBookViewModel.java    # ViewModel + LiveData
│   │   └── AddEditBookViewModelFactory.java
│   ├── booklist/
│   │   ├── BookListViewModel.java       # ViewModel untuk daftar buku
│   │   └── BookListViewModelFactory.java
│   ├── detail/
│   │   ├── BookDetailActivity.java      # Detail buku
│   │   ├── BookDetailViewModel.java     # ViewModel + LiveData
│   │   └── BookDetailViewModelFactory.java
│   └── adapter/
│       └── BookGridAdapter.java         # RecyclerView grid adapter
│
└── di/
    └── Injection.java                   # Manual Dependency Injection
```

### Penjelasan Layer

| Layer | Tanggung Jawab |
|-------|---------------|
| **Domain** | Business logic murni. Entity, repository interface, use cases. |
| **Data** | SQLite, DAO, data entity, mapper, repository implementation. |
| **Presentation** | MVVM pattern: Activity/Fragment + ViewModel + LiveData. |
| **DI** | Manual dependency injection via ViewModelFactory. |

### MVVM Pattern

- **ViewModel**: Menggunakan `androidx.lifecycle.ViewModel` - survives configuration changes
- **LiveData**: Reactive data observation untuk UI updates
- **ViewModelFactory**: Custom factory untuk dependency injection ke ViewModel

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
