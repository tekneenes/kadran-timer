# Kadran Screensaver (Android TV)

Bu depo, React tabanlı premium retro flip clock web uygulamasını (`kadran`) yerel bir Android TV Ekran Koruyucusu (Daydream Service) olarak derlemek için gereken tüm Android kaynak kodlarını ve yapılandırmalarını içerir.

## Özellikler

- **Yerel Ekran Koruyucu Entegrasyonu:** Android TV ayarlarından ekran koruyucu (Daydream) olarak seçilebilir.
- **TV Ayarları Arayüzü:** TV kumandasıyla odaklanıp yönetilebilecek büyük switch ve kutular içeren native ayar sayfası.
- **JavaScript & SharedPref Köprüsü:** Web uygulamasındaki ayarları (video kalitesi, saniyeler, arka plan, vb.) native SharedPreferences ile senkronize eder.
- **Çevrimdışı Çalışma:** React derleme dosyaları (`dist/*`) doğrudan uygulamanın asset klasörüne (`app/src/main/assets`) gömülmüştür. İnternet olmasa dahi saat çalışmaya devam eder.
- **Otomatik APK Derleme (CI/CD):** Her push yapıldığında GitHub Actions üzerinde otomatik olarak debug APK'sı derlenir ve indirilebilir olarak sunulur.

## Proje Yapısı

- `app/src/main/java/com/retro/flipclock/`: Native Java kodları.
  - `RetroClockSaver.java`: Ekran koruyucu servisi (DreamService).
  - `SettingsActivity.java`: TV ayarlarını değiştiren ekran.
  - `AndroidSettingsInterface.java`: Web ile Java arasındaki köprü.
- `app/src/main/res/`: Arayüz tasarımları, XML ayarlar ve metinler.
- `app/src/main/assets/`: Web uygulamasının derlenmiş statik dosyaları.

## GitHub Actions ile Derleme

Bu depoya her push yapıldığında `.github/workflows/build-apk.yml` otomatik çalışır ve debug APK'sını derler. Derlenen APK'yı indirmek için:
1. GitHub deposunda **Actions** sekmesine gidin.
2. Son başarılı workflow çalışmasına tıklayın.
3. Sayfanın en altındaki **Artifacts** kısmından `app-debug` zip dosyasını indirin.
