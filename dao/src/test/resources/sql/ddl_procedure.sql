-- Установка уровня совместимости, необходимый для работы функций(default 90)
ALTER DATABASE SDDB SET COMPATIBILITY_LEVEL = 100
-- SdGetRepCodes(id, direction) -> id, parent_id
-- SdGetWorkGroups(id, direction) -> id, parent_id
-- SdGetRoles(id, direction) -> id

-- id - идентификатор элемента, с короторого начинаем просматривать иерархию
-- direction: 1 - верх по иерархии, 0 - вниз

-- Примеры:
-- select * from SdGetRoles(281478237782753, 1);
-- select * from SdGetRoles(281478237782753, 0);
-- select * from SdGetRoles(281478103171215, 1);
-- select * from SdGetRoles(281478103171215, 0);
-- select * from SdGetRepCodes(281487452340226, 1);
-- select * from SdGetRepCodes(281487452340226, 0);


-- =============================================
-- Author:    Marat
-- Create date: 08.02.2018
-- Description:  Функция возвращает список кодов по иерархии, начиная с указанного элемента.
-- Arguments:
--            @NodeId  - идентификатор стартового элемента иерархии
--            @Direction - направление обхода иерархии. 1 - вверх, 0 -вниз
-- Returns:
--            Таблица (id DECIMAL(18), parent_id DECIMAL(18))
-- =============================================
CREATE FUNCTION dbo.SdGetRepCodes (@NodeId DECIMAL(18), @Direction DECIMAL(1))
RETURNS @Hierarchy TABLE (id DECIMAL(18), parent_id DECIMAL(18))
AS
BEGIN
WITH node(id, parent_id) AS (
SELECT rcd.rcd_oid, rcd.rcd_rcd_oid
FROM rep_codes rcd
WHERE rcd.rcd_oid = @NodeId
UNION ALL
SELECT rcd.rcd_oid, rcd.rcd_rcd_oid
FROM rep_codes rcd JOIN node ON
((node.parent_id = rcd.rcd_oid AND @Direction <> 0) OR (node.id = rcd.rcd_rcd_oid AND @Direction = 0))
)
INSERT INTO @Hierarchy SELECT id, parent_id FROM node
                                                 RETURN
END;

-- =============================================
-- Author:    Marat
-- Create date: 08.02.2018
-- Description:  Функция возвращает список рабочих групп по иерархии, начиная с указанной.
-- Arguments:
--            @NodeId  - идентификатор стартовой рабочей группы
--            @Direction - направление обхода иерархии. 1 - вверх, 0 -вниз
-- Returns:
--            Таблица (id DECIMAL(18), parent_id DECIMAL(18))
-- =============================================
CREATE FUNCTION dbo.SdGetWorkGroups (@NodeId DECIMAL(18), @Direction DECIMAL(1))
RETURNS @Hierarchy TABLE (id DECIMAL(18), parent_id DECIMAL(18))
AS
BEGIN
WITH node(id, parent_id) AS (
SELECT wg.wog_oid, wg.wog_parent
FROM itsm_workgroups wg
WHERE wg.wog_oid = @NodeId
UNION ALL
SELECT wg.wog_oid, wg.wog_parent
FROM itsm_workgroups wg JOIN node ON
((node.parent_id = wg.wog_oid AND @Direction <> 0) OR (node.id = wg.wog_parent AND @Direction = 0))
)
INSERT INTO @Hierarchy SELECT id, parent_id FROM node
                                                 RETURN
END;

-- =============================================
-- Author:    Marat
-- Create date: 08.02.2018
-- Description:  Функция возвращает список ролей по иерархии, начиная с указанной.
-- Arguments:
--            @NodeId  - идентификатор стартовой роли
--            @Direction - направление обхода иерархии. 1 - вверх, 0 -вниз
-- Returns:
--            Таблица (id DECIMAL(18))
-- =============================================
CREATE FUNCTION dbo.SdGetRoles (@NodeId DECIMAL(18), @Direction DECIMAL(1))
RETURNS @Hierarchy TABLE (id DECIMAL(18))
AS
BEGIN
WITH node(id) AS (
SELECT r.rol_oid
FROM rep_roles r
WHERE r.rol_oid = @NodeId
UNION ALL
SELECT r.rol_oid
FROM rep_roles r
JOIN node ON
(
(r.rol_oid IN
(SELECT l.ROI_ROL_PARENT FROM rep_roles_include l WHERE l.ROI_ROL_child = node.id) AND @Direction <> 0)
OR
(r.rol_oid IN
(SELECT l.ROI_ROL_child FROM rep_roles_include l WHERE l.ROI_ROL_PARENT = node.id) AND @Direction = 0)
)
)
INSERT INTO @Hierarchy SELECT id FROM node;
RETURN
END;

-- =============================================
-- Author:    NSychev
-- Create date: 10.04.2019
-- Description:  Функция возвращает список организаций по иерархии, начиная с указанной.
-- Arguments:
--            @NodeId  - идентификатор стартовой организации
--            @Direction - направление обхода иерархии. 1 - вверх, 0 -вниз
-- Returns:
--            Таблица (id DECIMAL(18), parent_id DECIMAL(18))
-- =============================================
CREATE FUNCTION dbo.SdGetOrganizations (@NodeId DECIMAL(18), @Direction DECIMAL(1))
  RETURNS @Hierarchy TABLE (id DECIMAL(18), parent_id DECIMAL(18))
AS
BEGIN
WITH node(id, parent_id) AS (
  SELECT org.org_oid, org.org_parent
  FROM itsm_organizations org
  WHERE org.org_oid = @NodeId
  UNION ALL
  SELECT org.org_oid, org.org_parent
  FROM itsm_organizations org JOIN node ON
                                      ((node.parent_id = org.org_oid AND @Direction <> 0) OR (node.id = org.org_parent AND @Direction = 0))
)
INSERT INTO @Hierarchy SELECT id, parent_id FROM node
                                                 RETURN
END;