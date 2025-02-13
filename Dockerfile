# استخدم صورة Java الرسمية
FROM openjdk:17

# تحديد مجلد العمل داخل الحاوية
WORKDIR /app

# نسخ جميع ملفات المشروع إلى الحاوية
COPY . .

# التأكد من الدخول إلى المجلد الذي يحتوي على ملفات .java
WORKDIR /app/src/networkproject3

# تجميع جميع ملفات Java داخل الحزمة الصحيحة
RUN javac -d . *.java

# تشغيل السيرفر (تأكد من صحة اسم الحزمة والملف الرئيسي)
CMD ["java", "networkproject3.Server"]
