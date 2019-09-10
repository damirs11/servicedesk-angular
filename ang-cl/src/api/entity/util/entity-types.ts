import { Enumerable } from 'src/common/decorator/enumerable.decorator';

/** @Deprecated **/
export const TYPEID_CHANGE = 724041768;
export const TYPEID_PROBLEM = 717488173;
export const TYPEID_WORKORDER = 556859410;
export const TYPEID_APPROVAL = 281478244794382;

/** ToDo заменить этим объектом использование констант, что выше **/
enum EntityTypes {
    Change = 724041768,
    Problem = 717488173,
    Workorder = 556859410,
    Approval = 281478244794382,
    ServiceCall = 563019801
}

// @Enumerable(false)
// export function getNameById(typeId) {
//     const keys = Object.keys(this);
//     for (const key in keys) {
//         if (this[key] === typeId) { return key; }
//     }
//     return null;
// }
