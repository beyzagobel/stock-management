## Stock Managament Android Application with Java Programming

Bu uygulama Android Studio ile Java Programalama dili, veritabanı olarak Firebase kullanılarak geliştirilmiştir.

1. Kullanıcıyı giriş ekranı karşılar.
2. Kullanıcı kayıtlı ve açık oturumu mevcut ise kullanıcı anasayfaya yönlendirilir.
3. Kullanıcı kayıtlı fakat açık oturumu yok ise kullanıcının tekrar oturum açması sağlanır.
4. Kullanıcı kayıtlı değil ise kayıt olması sağlanır.
5. Kullanıcı işlemleri olarak Depo Ekleme, Depo Güncelleme, Depo Silme(eğer deponun ürün veya ürünleri var ise seçenek sunulur) ,Depo veya Depolara Ürün Ekleme, Ürün Silme, Ürün Güncelleme 
işlemleri yapabilmektedir.

> Aşağıdaki resimde Firebase Realtime Database deki depolar ve ürünler adlı JSON ağaç yapıları gözükmektedir.
ürünler ağacındaki ilk id alanı kendi deposuna ait id'ye aittir. Bu sayede bu yapıyı kullanarak NoSql veritabanı olan 
Firebase içerisinde, ilişkisel veritabanı gibi bu iki ağaç arasında ilişki kurmamı sağlamış oldu.

![image](https://user-images.githubusercontent.com/78444522/198855061-16780380-d65f-422c-afb1-ee9cc0f15390.png)


> Depo'nun ürünlerini eklediğimiz ve aynı zamanda bir ilişki kurmamızı sağlayan kodun yapısı :

![image](https://user-images.githubusercontent.com/78444522/198855306-d6d84835-d2bf-4fed-96e1-f6362ab39a22.png)



### Projenin Ana Klasör Yapısı 

![image](https://user-images.githubusercontent.com/78444522/198854581-12552fc8-dd0a-441a-b176-60bfcc06e47c.png)


