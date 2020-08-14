import kotlinx.cinterop.CPointer
import platform.posix.*

fun main() {
    val fileName = readLine()
    val fp : CPointer<FILE>? = fopen(fileName, "r")
    var fileContents = ""

    var c = getc(fp)
    while (c != EOF) {
        fileContents += c.toChar()
        c = getc(fp)
    }
    fclose(fp)

    var newFileContents = "int main() {\nchar a[30000] = {0}; int p = 0;\n"
    fileContents.forEach {
        newFileContents += when(it) {
            '>' -> "p++;"
            '<' -> "p--;"
            '+' -> "a[p]++;"
            '-' -> "a[p]--;"
            '.' -> "putchar(a[p]);"
            ',' -> "a[p]=getchar();"
            '[' -> "while(a[p]) {\n"
            ']' -> "}\n"
            else -> ""
        }
    }
    newFileContents += "return 0;}"

    val outFile : CPointer<FILE>? = fopen("$fileName.c", "w")
    fputs(newFileContents, outFile)
    fclose(outFile)

    println(fileContents)
    readLine()
}