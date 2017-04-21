f2p = read.csv2("familyResults.csv")
f2p.atl = read.csv2("familyResultsAtl.csv")

m2a = read.csv2("makeResults.csv")
m2a.atl = read.csv2("makeResultsAtl.csv")

m2as = read.csv2("makeResultsSync.csv")
m2as.atl = read.csv2("syncAtlResult.csv")

f2p.means = aggregate(f2p, list(f2p$Size), mean)
f2p.atlmeans = aggregate(f2p.atl, list(f2p.atl$Size), mean)

m2a.means = aggregate(m2a, list(m2a$Size), mean)
m2a.atlmeans = aggregate(m2a.atl, list(m2a.atl$Size), mean)

m2as.means = aggregate(m2as, list(m2as$Size), mean)
m2as.atlmeans = aggregate(m2as.atl, list(m2as.atl$Size), mean)

factor = 1e-6

pdf(file="families2persons.pdf", width=6, height=3)
par(mar=c(4.3,4.0,0.3,0.3))
size = f2p.means$Size
plot(size, factor * f2p.atlmeans$Default, type="n", xlab="Number of model elements", ylab="Propagate updates [ms]",
     ylim=c(factor * min(f2p.means$UpdatesInc),factor * max(f2p.atlmeans$Default)), log="xy")
lines(size, factor * f2p.means$UpdatesBatch, col="blue")
points(size, factor * f2p.means$UpdatesBatch, pch=16, col="blue")
lines(size, factor * f2p.means$UpdatesInc, col="red")
points(size, factor * f2p.means$UpdatesInc, pch=2, col="red")
lines(size, factor * f2p.atlmeans$Default, col="green")
points(size, factor * f2p.atlmeans$Default, pch=8, col="green")
lines(size, factor * f2p.atlmeans$Emftvm, col="purple")
points(size, factor * f2p.atlmeans$Emftvm, pch=12, col="purple")
legend(10, factor * max(f2p.atlmeans$Default),
       c("NMF Synchronizations (Batch)", "NMF Synchronizations (Incremental)", "ATL Default", "ATL EMF/TVM"), 
       col=c('blue', 'red', 'green', 'purple'), pch=c(16,2, 8, 12), bty='n', lty=1)
dev.off()

pdf(file="make2ant.pdf", width=6, height=3)
par(mar=c(4.3,4.0,0.3,0.3))
size = m2a.means$Size
plot(size, factor * m2a.atlmeans$Default, type="n", xlab="Number of model elements", ylab="Propagate updates [ms]",
     ylim=c(factor * min(m2a.means$UpdatesInc),factor * max(m2a.atlmeans$Default)), log="xy")
lines(size, factor * m2a.means$UpdatesBatch, col="blue")
points(size, factor * m2a.means$UpdatesBatch, pch=16, col="blue")
lines(size, factor * m2a.means$UpdatesInc, col="red")
points(size, factor * m2a.means$UpdatesInc, pch=2, col="red")
lines(size, factor * m2a.atlmeans$Default, col="green")
points(size, factor * m2a.atlmeans$Default, pch=8, col="green")
lines(size, factor * m2a.atlmeans$Emftvm, col="purple")
points(size, factor * m2a.atlmeans$Emftvm, pch=12, col="purple")
legend(10, factor * max(m2a.atlmeans$Default),
       c("NMF Synchronizations (Batch)", "NMF Synchronizations (Incremental)", "ATL Default", "ATL EMF/TVM"), 
       col=c('blue', 'red', 'green', 'purple'), pch=c(16,2, 8, 12), bty='n', lty=1)
dev.off()


pdf(file="make2ant_sync.pdf", width=6, height=3)
par(mar=c(4.3,4.0,0.3,0.3))
size = m2as.means$Size
plot(size, factor * m2as.atlmeans$SyncATL, type="n", xlab="Number of model elements", ylab="Propagate updates [ms]",
     ylim=c(factor * min(m2as.means$UpdatesInc),factor * max(m2as.atlmeans$SyncATL)), log="xy")
lines(size, factor * m2as.means$UpdatesBatch, col="blue")
points(size, factor * m2as.means$UpdatesBatch, pch=16, col="blue")
lines(size, factor * m2as.means$UpdatesInc, col="red")
points(size, factor * m2as.means$UpdatesInc, pch=2, col="red")
lines(size, factor * m2as.atlmeans$SyncATL, col="green")
points(size, factor * m2as.atlmeans$SyncATL, pch=8, col="green")
legend(10, factor * max(m2as.atlmeans$SyncATL),
       c("NMF Synchronizations (Batch)", "NMF Synchronizations (Incremental)", "SyncATL"), 
       col=c('blue', 'red', 'green'), pch=c(16,2, 8), bty='n', lty=1)
dev.off()