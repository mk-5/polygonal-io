package pl.mk5.polygonal.verifytask

interface VerifyPolygonsTask {
    Class<? extends VerifyPolygonsTask> type = VerifyPolygonsDefaultTask

    void verifyPolygons()
}