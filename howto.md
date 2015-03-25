# How-Tos #

## Getting started ##
  1. Install [Window Builder](http://www.eclipse.org/windowbuilder/download.php) within eclipse
  1. Install postgres
  1. create a database
  1. Set the correct test-database connection url inside `resources/dbconfig.txt` (for example: `URL=jdbc:postgresql://localhost/<your-databasename>?user=<username>&password=<your-password>`)
  1. Remove `swt-4.2.1-win32-win32-x86_64.jar` from your buildpath and add from `lib/` the corresponding swt library