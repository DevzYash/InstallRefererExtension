# Add any ProGuard configurations specific to this
# extension here.

-keep public class com.yash.playstoreapi.Playstoreapi {
    public *;
 }
-keeppackagenames gnu.kawa**, gnu.expr**

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses 'com/yash/playstoreapi/repack'
-flattenpackagehierarchy
-dontpreverify
