import Foundation
import Combine
import EVLib

extension Publishers {
    static func createPublisher<T>(
        wrapper: KmpFlow<T>
    ) -> AnyPublisher<T?, Error> {
        var job: KmpSubscription? = nil  //Kotlinx_coroutines_coreJob? = nil
        return Deferred {
            Future { promise in
                job = wrapper.subscribe(onEach: { value in
                    promise(.success(value))
                }, onCompletion: { throwable in
                    promise(.failure(throwable.toError()))
                })
            }.handleEvents(receiveCancel: {
                job?.unsubscribe()
            })
        }.eraseToAnyPublisher()
    }
}

private extension Optional<KotlinThrowable> {
    func toError() -> Error {
        self?.asError() ?? NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey : debugDescription])
    }
}
