/**
 * Меняет в дексприпторе поля объяекта enumerable на value
 * @param value
 * @return {Function}
 * @constructor
 */
function Enumerable(value) {
  return function(target, key, descriptor) {
    descriptor.enumerable = value;
    return descriptor;
  };
}

export { Enumerable };
