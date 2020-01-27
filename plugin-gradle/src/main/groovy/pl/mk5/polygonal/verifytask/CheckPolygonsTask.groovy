package pl.mk5.polygonal.verifytask

interface CheckPolygonsTask {
    Class<? extends CheckPolygonsTask> type = CheckPolygonsDefaultTask

    void checkPolygons()
}