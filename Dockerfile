# استخدم صورة Java الرسمية
FROM openjdk:17

# تحديد مجلد العمل داخل الحاوية
WORKDIR /app

# نسخ جميع ملفات المشروع إلى الحاوية
COPY . /app

# تجميع جميع ملفات Java داخل المجلد إذا كان هناك حزم (Packages)
RUN javac -d . $(find . -name "*.java")

# تشغيل التطبيق (تأكد من استخدام اسم الحزمة إذا كان `Main` داخل package)
CMD ["java", "networkproject3.Main"]

