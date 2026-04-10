package com.example.springbootapi.openloop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class inMemoryTest {

    private inMemory storage;

    @BeforeEach
    public void setup() {
        storage = new inMemory();
    }

    // --- addFile ---

    @Test
    public void addFile_newFile_returnsTrue() {
        boolean result = storage.addFile("file1.txt", 100);
        System.out.println("addFile('file1.txt', 100) => got: " + result + " | expected: true");
        assertTrue(result);
    }

    @Test
    public void addFile_duplicate_returnsFalse() {
        storage.addFile("file1.txt", 100);
        boolean result = storage.addFile("file1.txt", 200);
        System.out.println("addFile duplicate => got: " + result + " | expected: false");
        assertFalse(result);
    }

    @Test
    public void addFile_duplicate_doesNotOverwriteSize() {
        storage.addFile("file1.txt", 100);
        storage.addFile("file1.txt", 200); // should be ignored
        Integer size = storage.getFileSize("file1.txt");
        System.out.println("size after duplicate add => got: " + size + " | expected: 100");
        assertEquals(100, size);
    }

    // --- copyFile ---

    @Test
    public void copyFile_validCopy_returnsTrue() {
        storage.addFile("a.txt", 50);
        boolean result = storage.copyFile("a.txt", "b.txt");
        System.out.println("copyFile('a.txt', 'b.txt') => got: " + result + " | expected: true");
        assertTrue(result);
    }

    @Test
    public void copyFile_copiedFileHasSameSize() {
        storage.addFile("a.txt", 50);
        storage.copyFile("a.txt", "b.txt");
        Integer size = storage.getFileSize("b.txt");
        System.out.println("size of copied file => got: " + size + " | expected: 50");
        assertEquals(50, size);
    }

    @Test
    public void copyFile_sourceDoesNotExist_returnsFalse() {
        boolean result = storage.copyFile("ghost.txt", "b.txt");
        System.out.println("copyFile missing source => got: " + result + " | expected: false");
        assertFalse(result);
    }

    @Test
    public void copyFile_destinationAlreadyExists_returnsFalse() {
        storage.addFile("a.txt", 50);
        storage.addFile("b.txt", 99);
        boolean result = storage.copyFile("a.txt", "b.txt");
        System.out.println("copyFile dest exists => got: " + result + " | expected: false");
        assertFalse(result);
    }

    @Test
    public void copyFile_destinationAlreadyExists_doesNotOverwrite() {
        storage.addFile("a.txt", 50);
        storage.addFile("b.txt", 99);
        storage.copyFile("a.txt", "b.txt");
        Integer size = storage.getFileSize("b.txt");
        System.out.println("b.txt size after blocked copy => got: " + size + " | expected: 99");
        assertEquals(99, size);
    }

    // --- getFileSize ---

    @Test
    public void getFileSize_existingFile_returnsSize() {
        storage.addFile("img.png", 512);
        Integer size = storage.getFileSize("img.png");
        System.out.println("getFileSize('img.png') => got: " + size + " | expected: 512");
        assertEquals(512, size);
    }

    @Test
    public void getFileSize_nonExistentFile_returnsNull() {
        Integer size = storage.getFileSize("nope.txt");
        System.out.println("getFileSize missing file => got: " + size + " | expected: null");
        assertNull(size);
    }

    // --- chained scenario (addFile -> copy -> getFileSize) ---

    @Test
    public void scenario_addThenCopyThenCheckSize() {
        boolean added    = storage.addFile("original.txt", 300);
        boolean copied   = storage.copyFile("original.txt", "backup.txt");
        Integer origSize = storage.getFileSize("original.txt");
        Integer copySize = storage.getFileSize("backup.txt");

        System.out.println("added       => got: " + added    + " | expected: true");
        System.out.println("copied      => got: " + copied   + " | expected: true");
        System.out.println("origSize    => got: " + origSize + " | expected: 300");
        System.out.println("copySize    => got: " + copySize + " | expected: 300");

        assertTrue(added);
        assertTrue(copied);
        assertEquals(300, origSize);
        assertEquals(300, copySize);
    }

    // --- deleteFile ---

    @Test
    public void deleteFile_existingFile_returnsTrue() {
        storage.addFile("del.txt", 10);
        boolean result = storage.deleteFile("del.txt");
        System.out.println("deleteFile existing => got: " + result + " | expected: true");
        assertTrue(result);
    }

    @Test
    public void deleteFile_nonExistentFile_returnsFalse() {
        boolean result = storage.deleteFile("ghost.txt");
        System.out.println("deleteFile missing => got: " + result + " | expected: false");
        assertFalse(result);
    }

    @Test
    public void deleteFile_fileNoLongerAccessibleAfterDelete() {
        storage.addFile("del.txt", 10);
        storage.deleteFile("del.txt");
        Integer size = storage.getFileSize("del.txt");
        System.out.println("getFileSize after delete => got: " + size + " | expected: null");
        assertNull(size);
    }

    // --- findFilesByPrefix ---

    @Test
    public void findFilesByPrefix_matchesPrefix() {
        storage.addFile("img_a.png", 200);
        storage.addFile("img_b.png", 100);
        storage.addFile("doc.txt", 50);
        List<String> result = storage.findFilesByPrefix("img");
        System.out.println("findFilesByPrefix('img') => got: " + result + " | expected: [img_a.png, img_b.png]");
        assertEquals(2, result.size());
        assertTrue(result.contains("img_a.png"));
        assertTrue(result.contains("img_b.png"));
    }

    @Test
    public void findFilesByPrefix_sortedBySizeDescThenNameAsc() {
        storage.addFile("img_b.png", 200);
        storage.addFile("img_a.png", 200);
        storage.addFile("img_c.png", 100);
        List<String> result = storage.findFilesByPrefix("img");
        System.out.println("findFilesByPrefix sorted => got: " + result + " | expected: [img_a.png, img_b.png, img_c.png]");
        assertEquals(List.of("img_a.png", "img_b.png", "img_c.png"), result);
    }

    @Test
    public void findFilesByPrefix_noMatch_returnsEmpty() {
        storage.addFile("doc.txt", 50);
        List<String> result = storage.findFilesByPrefix("img");
        System.out.println("findFilesByPrefix no match => got: " + result + " | expected: []");
        assertTrue(result.isEmpty());
    }

    @Test
    public void findFilesByPrefix_emptyStorage_returnsEmpty() {
        List<String> result = storage.findFilesByPrefix("any");
        System.out.println("findFilesByPrefix empty storage => got: " + result + " | expected: []");
        assertTrue(result.isEmpty());
    }

    // --- addUser ---

    @Test
    public void addUser_newUser_returnsTrue() {
        boolean result = storage.addUser("alice", 500);
        System.out.println("addUser('alice', 500) => got: " + result + " | expected: true");
        assertTrue(result);
    }

    @Test
    public void addUser_duplicateUser_returnsFalse() {
        storage.addUser("alice", 500);
        boolean result = storage.addUser("alice", 1000);
        System.out.println("addUser duplicate => got: " + result + " | expected: false");
        assertFalse(result);
    }

    // --- addFileByUser ---

    @Test
    public void addFileByUser_validUser_returnsTrue() {
        storage.addUser("alice", 500);
        boolean result = storage.addFileByUser("alice", "doc.txt", 100);
        System.out.println("addFileByUser valid => got: " + result + " | expected: true");
        assertTrue(result);
    }

    @Test
    public void addFileByUser_userDoesNotExist_returnsFalse() {
        boolean result = storage.addFileByUser("ghost", "doc.txt", 100);
        System.out.println("addFileByUser no user => got: " + result + " | expected: false");
        assertFalse(result);
    }

    @Test
    public void addFileByUser_fileAlreadyExists_returnsFalse() {
        storage.addUser("alice", 500);
        storage.addFile("doc.txt", 100);
        boolean result = storage.addFileByUser("alice", "doc.txt", 100);
        System.out.println("addFileByUser file exists => got: " + result + " | expected: false");
        assertFalse(result);
    }

    @Test
    public void addFileByUser_exceedsCapacity_returnsFalse() {
        storage.addUser("alice", 100);
        boolean result = storage.addFileByUser("alice", "big.txt", 200);
        System.out.println("addFileByUser over capacity => got: " + result + " | expected: false");
        assertFalse(result);
    }

    @Test
    public void addFileByUser_exactlyFillsCapacity_returnsTrue() {
        storage.addUser("alice", 100);
        boolean result = storage.addFileByUser("alice", "exact.txt", 100);
        System.out.println("addFileByUser exact capacity => got: " + result + " | expected: true");
        assertTrue(result);
    }

    @Test
    public void addFileByUser_secondFileExceedsRemainingCapacity_returnsFalse() {
        storage.addUser("alice", 100);
        storage.addFileByUser("alice", "first.txt", 80);
        boolean result = storage.addFileByUser("alice", "second.txt", 30);
        System.out.println("addFileByUser second exceeds remaining => got: " + result + " | expected: false");
        assertFalse(result);
    }

    @Test
    public void addFileByUser_fileSizeAccessibleViaGetFileSize() {
        storage.addUser("alice", 500);
        storage.addFileByUser("alice", "report.pdf", 250);
        Integer size = storage.getFileSize("report.pdf");
        System.out.println("getFileSize after addFileByUser => got: " + size + " | expected: 250");
        assertEquals(250, size);
    }

    // --- updateCapacity ---

    @Test
    public void updateCapacity_nonExistentUser_returnsNull() {
        Integer result = storage.updateCapacity("ghost", 1000);
        System.out.println("updateCapacity no user => got: " + result + " | expected: null");
        assertNull(result);
    }

    @Test
    public void updateCapacity_userWithNoFiles_returnsZero() {
        storage.addUser("alice", 500);
        Integer result = storage.updateCapacity("alice", 1000);
        System.out.println("updateCapacity no files => got: " + result + " | expected: 0");
        assertEquals(0, result);
    }

    @Test
    public void updateCapacity_returnsCountOfUserFiles() {
        storage.addUser("alice", 500);
        storage.addFileByUser("alice", "a.txt", 100);
        storage.addFileByUser("alice", "b.txt", 100);
        Integer result = storage.updateCapacity("alice", 1000);
        System.out.println("updateCapacity file count => got: " + result + " | expected: 2");
        assertEquals(2, result);
    }

    @Test
    public void updateCapacity_onlyCountsFilesOwnedByThatUser() {
        storage.addUser("alice", 500);
        storage.addUser("bob", 500);
        storage.addFileByUser("alice", "a.txt", 100);
        storage.addFileByUser("bob", "b.txt", 100);
        storage.addFile("c.txt", 100); // no owner
        Integer result = storage.updateCapacity("alice", 1000);
        System.out.println("updateCapacity only alice files => got: " + result + " | expected: 1");
        assertEquals(1, result);
    }

    @Test
    public void updateCapacity_newCapacityAllowsMoreFiles() {
        storage.addUser("alice", 100);
        storage.addFileByUser("alice", "a.txt", 100); // fills capacity
        storage.updateCapacity("alice", 300);
        boolean result = storage.addFileByUser("alice", "b.txt", 100);
        System.out.println("addFileByUser after capacity increase => got: " + result + " | expected: true");
        assertTrue(result);
    }

    // --- big chained scenario ---

    @Test
    public void scenario_fullWorkflow() {
        // setup two users
        boolean aliceAdded = storage.addUser("alice", 500);
        boolean bobAdded   = storage.addUser("bob", 200);
        System.out.println("addUser alice => got: " + aliceAdded + " | expected: true");
        System.out.println("addUser bob   => got: " + bobAdded   + " | expected: true");
        assertTrue(aliceAdded);
        assertTrue(bobAdded);

        // alice adds files
        boolean f1 = storage.addFileByUser("alice", "report.pdf", 200);
        boolean f2 = storage.addFileByUser("alice", "photo.jpg",  200);
        boolean f3 = storage.addFileByUser("alice", "extra.txt",    1); // should fail — full
        System.out.println("alice report.pdf (200) => got: " + f1 + " | expected: true");
        System.out.println("alice photo.jpg  (200) => got: " + f2 + " | expected: true");
        System.out.println("alice extra.txt  (1)   => got: " + f3 + " | expected: false (capacity full)");
        assertTrue(f1);
        assertTrue(f2);
        assertFalse(f3);

        // bob adds a file
        boolean b1 = storage.addFileByUser("bob", "budget.xlsx", 150);
        System.out.println("bob budget.xlsx (150) => got: " + b1 + " | expected: true");
        assertTrue(b1);

        // check sizes
        Integer reportSize = storage.getFileSize("report.pdf");
        Integer photoSize  = storage.getFileSize("photo.jpg");
        System.out.println("getFileSize report.pdf => got: " + reportSize + " | expected: 200");
        System.out.println("getFileSize photo.jpg  => got: " + photoSize  + " | expected: 200");
        assertEquals(200, reportSize);
        assertEquals(200, photoSize);

        // copy report -> archive
        boolean copied = storage.copyFile("report.pdf", "report_archive.pdf");
        System.out.println("copyFile report -> archive => got: " + copied + " | expected: true");
        assertTrue(copied);
        assertEquals(200, storage.getFileSize("report_archive.pdf"));

        // delete original report — alice usage drops by 200
        boolean deleted = storage.deleteFile("report.pdf");
        System.out.println("deleteFile report.pdf => got: " + deleted + " | expected: true");
        assertTrue(deleted);
        assertNull(storage.getFileSize("report.pdf"));

        // alice can now add a new file since usage dropped
        boolean f4 = storage.addFileByUser("alice", "notes.txt", 150);
        System.out.println("alice notes.txt after delete => got: " + f4 + " | expected: true");
        assertTrue(f4);

        // findFilesByPrefix — all alice's remaining files starting with no common prefix
        List<String> jpgFiles = storage.findFilesByPrefix("photo");
        System.out.println("findFilesByPrefix('photo') => got: " + jpgFiles + " | expected: [photo.jpg]");
        assertEquals(1, jpgFiles.size());
        assertEquals("photo.jpg", jpgFiles.get(0));

        // updateCapacity for alice: returns count of alice-owned files
        // alice owns: photo.jpg, notes.txt (report_archive is a copy with alice's userId, report.pdf deleted)
        Integer aliceFileCount = storage.updateCapacity("alice", 1000);
        System.out.println("updateCapacity alice => got: " + aliceFileCount + " | check count of alice files");
        assertNotNull(aliceFileCount);

        // after capacity update, alice can add another large file
        boolean f5 = storage.addFileByUser("alice", "video.mp4", 400);
        System.out.println("alice video.mp4 after capacity bump => got: " + f5 + " | expected: true");
        assertTrue(f5);
    }
}
