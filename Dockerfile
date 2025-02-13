# استخدم صورة Java الرسمية
FROM openjdk:17

# تحديد مجلد العمل داخل الحاوية
WORKDIR /app

# نسخ جميع ملفات المشروع إلى الحاوية
COPY . .

# التأكد من الدخول إلى المجلد الذي يحتوي على ملفات `.java`
WORKDIR /app/src

# تجميع جميع ملفات Java
RUN javac -d . $(find . -type f -name "*.java")

# تشغيل السيرفر (تأكد من اسم الحزمة الصحيح)
CMD ["java", "networkproject3.Server"]
