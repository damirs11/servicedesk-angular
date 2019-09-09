function Nullable(clazz,instantiate) {
    return data => {
        if (data == null) return data;
        if (instantiate) return new clazz(data);
        return clazz(data)
    }
}

function Instantiate(clazz) {
    return data => {
        if (data == null) return data;
        return new clazz(data);
    }
}

export {Nullable, Instantiate}