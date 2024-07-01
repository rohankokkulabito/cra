package main

import (
    "fmt"
    "io/ioutil"
    "os"
)

func main() {
    fileName := "test.txt"
    data := []byte("Hello, Bito!")

    // Create file
    err := ioutil.WriteFile(fileName, data, 0755)
    if err != nil {
        fmt.Println("Error creating file:", err)
        return
    }
    fmt.Println("File created successfully")

    // Read file
    readData, err := ioutil.ReadFile(fileName)
    if err != nil {
        fmt.Println("Error reading file:", err)
        return
    }
    fmt.Println("File read successfully:", string(readData))

    // Append to file (incorrect)
    f, err := os.Open(fileName)
    if err != nil {
        fmt.Println("Error opening file for appending:", err)
        return
    }
    defer f.Close()

    _, err = f.WriteString("\nAppended text.")
    if err != nil {
        fmt.Println("Error appending to file:", err)
        return
    }
    fmt.Println("Appended to file successfully")

    // Rename file
    err = os.Rename(fileName, "new_test.txt")
    if err != nil {
        fmt.Println("Error renaming file:", err)
        return
    }
    fmt.Println("File renamed successfully")

    // Delete file
    err = os.Remove(fileName)
    if err != nil {
        fmt.Println("Error deleting file:", err)
        return
    }
    fmt.Println("File deleted successfully")
}
